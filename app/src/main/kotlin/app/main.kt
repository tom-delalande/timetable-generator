package app

import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.server.engine.embeddedServer
import io.ktor.server.html.respondHtml
import io.ktor.server.netty.Netty
import io.ktor.server.request.receiveMultipart
import io.ktor.server.response.respond
import io.ktor.server.response.respondFile
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.util.cio.writeChannel
import io.ktor.utils.io.copyAndClose
import java.io.File
import java.time.LocalTime
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
import repository.InMemory
import repository.Repository
import repository.Timetable
import view.Event
import view.home
import view.timetable
import view.timetableDocs

fun main() {
    val repository = InMemory

    embeddedServer(Netty, port = 9090) {
        routing {
            index()
            downloadExample()
            upload(repository)
            timetable(repository)
            test()
        }
    }.start(wait = true)
}

fun Routing.index() = get("/") {
    call.respondHtml {
        index {
            home()
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

fun Routing.timetable(repository: Repository) = get("/timetable/{id}") {
    val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
    val timetable = when (id) {
        "example" -> Timetable(
            events = exampleEvents
        )

        else -> repository.load(id) ?: return@get call.respond(HttpStatusCode.BadRequest)
    }
    call.respondHtml {
        index {
            timetable(timetable)
        }
    }
}

fun Routing.downloadExample() = get("/timetable-converter-example.csv") {
    call.respondFile(File("timetable-converter-example.csv"))
}

fun Routing.upload(repository: Repository) = post("/upload") {
    val file = File.createTempFile("aaa", "csv")

    val multipart = call.receiveMultipart()
    val part = multipart.readPart() ?: return@post call.respond(HttpStatusCode.BadRequest)
    if (part !is PartData.FileItem) return@post call.respond(HttpStatusCode.BadRequest)
    part.provider().copyAndClose(file.writeChannel())

    val events = file.toEvents()
    val id = UUID.randomUUID().toString()
    repository.save(id, Timetable(events))
    call.respondHtml {
        body {
            timetable(Timetable(events))
            timetableDocs(id)
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
