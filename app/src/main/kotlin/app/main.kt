package app

import hero
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.html.respondHtml
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.request.receiveMultipart
import io.ktor.server.request.userAgent
import io.ktor.server.response.header
import io.ktor.server.response.respond
import io.ktor.server.response.respondFile
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import io.ktor.util.cio.writeChannel
import io.ktor.utils.io.copyAndClose
import java.io.File
import java.time.Instant
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.UUID
import kotlinx.html.FlowContent
import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.head
import kotlinx.html.id
import kotlinx.html.script
import kotlinx.html.title
import org.apache.commons.csv.CSVFormat
import org.slf4j.event.Level
import repository.PaymentRepository
import repository.TimeTableRepository
import repository.Timetable
import repository.exampleTimetable
import view.Event
import view.components.error
import view.sections.docs
import view.sections.faq
import view.sections.features
import view.sections.footer
import view.timetable
import view.timetableWithBorder

fun main() {
    checkEnv()
    val timeTableRepository = TimeTableRepository.InMemory
    val paymentRepository = PaymentRepository.InMemory

    embeddedServer(Netty, port = 9090) {
        install(CallLogging) {
            level = Level.INFO
        }

        routing {
            index()
            downloadExample()
            upload(timeTableRepository)
            timetable(timeTableRepository, paymentRepository)
            payments(paymentRepository, timeTableRepository)
            test()
        }
    }.start(wait = true)
}

fun Routing.index() = get("/") {
    call.respondHtml {
        index {
            hero()
            div {
                classes = setOf("relative overflow-hidden pt-16", "pb-64")
                div {
                    classes = setOf("mx-auto max-w-7xl px-6 lg:px-8")
                    div {
                        classes = setOf(
                            "mb-[-12%]",
                            "rounded-xl",
                            "shadow-2xl",
                            "ring-1",
                            "ring-gray-900/10",
                            "max-h-[600px]",
                            "overflow-hidden"
                        )
                        timetable(exampleTimetable)
                    }
                }
            }
            features()
            footer()
        }
    }
}

fun Routing.test() = get("/test") {
    val testFiles = File("timetables/test").listFiles().map { it.toEvents() }
    call.respondHtml {
        index {
            div {
                classes = setOf("flex", "flex-col", "gap-2")
                testFiles.map {
                    timetable(Timetable(UUID.randomUUID(), UUID.randomUUID(), it))
                }
            }
        }
    }
}

val exampleEvents = File("timetables/timetable-converter-example.csv").toEvents()

fun Routing.timetable(timeTableRepository: TimeTableRepository, paymentRepository: PaymentRepository) =
    route("/timetable/{id}") {
        get {
            val publicId =
                call.parameters["id"]?.let { UUID.fromString(it) } ?: return@get call.respond(HttpStatusCode.BadRequest)
            val embedded = call.queryParameters["embedded"] == "true"
            val timetable = when (publicId) {
                UUID.fromString("00000000-0000-0000-0000-000000000000"),
                    -> Timetable(publicId, publicId, events = exampleEvents)

                else -> timeTableRepository.loadByPublicId(publicId)
                    ?: return@get call.respond(HttpStatusCode.BadRequest)
            }
            val subscribed = isSubscribed(
                timetable.privateId,
                timetable,
                paymentRepository
            ) || publicId == UUID.fromString("00000000-0000-0000-0000-000000000000")

            if (subscribed) {
                return@get call.respondHtml {
                    index {
                        if (embedded) {
                            timetable(timetable)
                        } else {
                            timetableWithBorder(timetable)
                        }
                    }
                }
            }

            // TODO: Add error page when with CTA for purchase
            call.respondHtml {
                +"This timetable is no longer available. You can re-enabled it by enabling hosting"
            }
        }
        get("/edit") {
            val privateId =
                call.parameters["id"]?.let { UUID.fromString(it) } ?: return@get call.respond(HttpStatusCode.BadRequest)
            val timetable = when (privateId) {
                UUID.fromString("00000000-0000-0000-0000-000000000000") -> exampleTimetable
                else -> timeTableRepository.loadByPrivateId(privateId)
                    ?: return@get call.respond(HttpStatusCode.BadRequest)
            }

            val subscribed = paymentRepository.check(privateId)

            call.respondHtml {
                index {
                    div {
                        classes = setOf("relative overflow-hidden py-16")
                        div {
                            classes = setOf("mx-auto max-w-7xl px-6 lg:px-8")
                            div {
                                classes = setOf("rounded-xl", "shadow-2xl", "ring-1", "ring-gray-900/10", "pb-4")
                                timetable(timetable)
                            }
                        }
                    }
                    docs(timetable, subscribed)
                    if (!subscribed) {
                        faq()
                    }
                }
            }
        }
    }

fun Routing.downloadExample() = get("/timetable-converter-example.csv") {
    call.respondFile(File("timetable-converter-example.csv"))
}

fun Routing.upload(timeTableRepository: TimeTableRepository) = post("/upload") {
    val file = File.createTempFile("aaa", "csv")

    val multipart = call.receiveMultipart()
    val part = multipart.readPart() ?: return@post call.respondHtml {
        body {
            error("Failed to upload CSV file", "Are you sure you uploaded a file?")
        }
    }

    if (part !is PartData.FileItem) return@post call.respondHtml {
        body {
            error("Failed to upload CSV file", "Are you sure you uploaded a file?")
        }
    }

    val events = try {
        part.provider().copyAndClose(file.writeChannel())
        file.toEvents()
    } catch (exception: Exception) {
        return@post call.respondHtml {
            body {
                error("Failed to process CSV file", "Double check the template provided")
            }
        }
    }

    val existingPrivateId = call.queryParameters["timetable"]?.let { UUID.fromString(it) }

    val privateId = if (existingPrivateId != null) {
        val timetable = timeTableRepository.loadByPrivateId(existingPrivateId) ?: return@post call.respond(
            HttpStatusCode.BadRequest
        )
        timeTableRepository.update(existingPrivateId, timetable.copy(events = events))
        existingPrivateId
    } else {
        val privateId = UUID.randomUUID()
        val publicId = UUID.randomUUID()
        timeTableRepository.save(
            Timetable(
                privateId = privateId,
                publicId = publicId,
                events = events,
            )
        )
        privateId
    }

    call.response.header("HX-Redirect", "/timetable/${privateId}/edit")
    call.respond(HttpStatusCode.OK)
}

fun Routing.payments(paymentRepository: PaymentRepository, timeTableRepository: TimeTableRepository) =
    route("/payments") {
        get("/purchase/timetable/{privateId}") {
            val timetableId =
                call.parameters["privateId"]?.let { UUID.fromString(it) }
                    ?: return@get call.respond(HttpStatusCode.BadRequest)
            val paymentId = UUID.randomUUID()
            paymentRepository.start(timetableId, paymentId)

            val sessionUrl = Payments.createSession(paymentId, timetableId)
            call.respondRedirect(sessionUrl.url)
        }

        get("/success/payment/{privateId}") {
            val paymentId = call.parameters["privateId"]?.let { UUID.fromString(it) } ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "paymentId is required"
            )
            val timetableId = paymentRepository.get(paymentId) ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "No timetableId found for payment id $paymentId"
            )
            paymentRepository.success(paymentId)
            call.respondRedirect("/timetable/$timetableId/edit")
        }
    }

fun File.toEvents(): List<Event> {
    val csvFormat = CSVFormat.DEFAULT.builder()
        .setHeader("Day", "Start", "End", "Title", "Colour")
        .setSkipHeaderRecord(true)
        .get()

    val parser = csvFormat.parse(reader())
    return parser.records.map {
        Event(
            day = it.get("Day").let {
                when (it) {
                    "Monday" -> 0
                    "Tuesday" -> 1
                    "Wednesday" -> 2
                    "Thursday" -> 3
                    "Friday" -> 4
                    "Saturday" -> 5
                    "Sunday" -> 6
                    else -> 0
                }
            },
            startTime = it.get("Start").let { LocalTime.parse(it) },
            endTime = it.get("End").let { LocalTime.parse(it) },
            title = it.get("Title"),
            colour = when (it.get("Colour")) {
                "black" -> "stone"
                else -> it.get("Colour")
            },
        )
    }

}

fun HTML.index(page: FlowContent.() -> Unit) {
    classes = setOf("h-full", "bg-gray-100")
    head {
        title {
            +"Timetable"
        }
        script { src = "https://cdn.tailwindcss.com" }
        script { src = "https://unpkg.com/htmx.org@1.9.10" }
    }
    body {
        div {
            id = "content"
            page()
        }
    }
}


fun isSubscribed(privateId: UUID, timetable: Timetable, paymentRepository: PaymentRepository): Boolean {
    val paid = paymentRepository.check(privateId)
    val isTrialPeriod = timetable.createdAt.isAfter(Instant.now().minus(7, ChronoUnit.DAYS))
            && Environment.trialEnabled
    return paid || isTrialPeriod
}