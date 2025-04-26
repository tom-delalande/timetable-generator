package app

import hero
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.server.engine.embeddedServer
import io.ktor.server.html.respondHtml
import io.ktor.server.netty.Netty
import io.ktor.server.request.receiveMultipart
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
import repository.PaymentRepository
import repository.TimeTableRepository
import repository.Timetable
import view.Event
import view.components.error
import view.sections.features
import view.timetable
import view.timetableDocs

fun main() {
    checkEnv()
    val timeTableRepository = TimeTableRepository.InMemory
    val paymentRepository = PaymentRepository.InMemory

    embeddedServer(Netty, port = 9090) {
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
                        classes = setOf("mb-[-12%]", "rounded-xl", "shadow-2xl", "ring-1", "ring-gray-900/10", "max-h-[600px]", "overflow-hidden")
                        timetable(Timetable(exampleEvents))
                    }
                }
            }
            features()
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
                    timetable(Timetable(it))
                }
            }
        }
    }
}

val exampleEvents = File("timetables/timetable-converter-example.csv").toEvents()

fun Routing.timetable(timeTableRepository: TimeTableRepository, paymentRepository: PaymentRepository) =
    route("/timetable/{id}") {
        get {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val timetable = when (id) {
                "example" -> Timetable(events = exampleEvents)
                else -> timeTableRepository.load(UUID.fromString(id))
                    ?: return@get call.respond(HttpStatusCode.BadRequest)
            }

            val paid = id == "example" || paymentRepository.check(UUID.fromString(id))
            val isTrialPeriod = timetable.createdAt.isAfter(Instant.now().minus(7, ChronoUnit.DAYS))
                    && Environment.trialEnabled
            if (isTrialPeriod || paid) {
                return@get call.respondHtml {
                    index {
                        timetable(timetable)
                    }
                }
            }

            // TODO: Add error page when with CTA for purchase
            call.respondHtml {
                +"This timetable is no longer available. You can re-enabled it by enabling hosting"
            }
        }
        get("/edit") {
            val id =
                call.parameters["id"]?.let { UUID.fromString(it) } ?: return@get call.respond(HttpStatusCode.BadRequest)
            val timetable = timeTableRepository.load(id) ?: return@get call.respond(HttpStatusCode.BadRequest)

            call.respondHtml {
                index {
                    timetable(timetable)
                    timetableDocs(id)
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

    val id = UUID.randomUUID()
    timeTableRepository.save(id, Timetable(events))

    call.response.header("HX-Redirect", "/timetable/${id}/edit")
    call.respond(HttpStatusCode.OK)
}

fun Routing.payments(paymentRepository: PaymentRepository, timeTableRepository: TimeTableRepository) =
    route("/payments") {
        get("/purchase/timetable/{id}") {
            val timetableId =
                call.parameters["id"]?.let { UUID.fromString(it) } ?: return@get call.respond(HttpStatusCode.BadRequest)
            val paymentId = UUID.randomUUID()
            paymentRepository.start(timetableId, paymentId)

            val sessionUrl = Payments.createSession(paymentId, timetableId)
            call.respondRedirect(sessionUrl.url)
        }

        get("/success/payment/{id}") {
            val paymentId = call.parameters["id"]?.let { UUID.fromString(it) } ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "paymentId is required"
            )
            val timetableId = paymentRepository.get(paymentId) ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "No timetableId found for payment id $paymentId"
            )
            val timetable = timeTableRepository.load(timetableId) ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "No timetable found for id $timetableId"
            )
            paymentRepository.success(paymentId)
            call.respondHtml {
                body {
                    timetable(timetable)
                    timetableDocs(timetableId)
                }
            }
        }

        get("/cancel/timetable/{id}") {
            val timetableId =
                call.parameters["id"]?.let { UUID.fromString(it) } ?: return@get call.respond(HttpStatusCode.BadRequest)
            paymentRepository.cancel(timetableId)
            call.respondHtml {
                +"Cancelled"
            }
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
