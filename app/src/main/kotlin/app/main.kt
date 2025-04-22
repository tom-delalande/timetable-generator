package app

import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.http.content.streamProvider
import io.ktor.server.engine.embeddedServer
import io.ktor.server.html.respondHtml
import io.ktor.server.netty.Netty
import io.ktor.server.request.receiveChannel
import io.ktor.server.request.receiveMultipart
import io.ktor.server.response.header
import io.ktor.server.response.respond
import io.ktor.server.response.respondFile
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.util.cio.writeChannel
import io.ktor.utils.io.copyAndClose
import io.ktor.utils.io.readUTF8Line
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

fun main() {
    val repository = InMemory

    embeddedServer(Netty, port = 9090) {
        routing {
            index()
            downloadExample()
            upload(repository)
            timetable(repository)
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

val exampleEvents = listOf(
    Event(
        LocalTime.of(6, 0),
        LocalTime.of(7, 0),
        "Breakfast 2",
        "blue",
        2,
    ),
    Event(
        LocalTime.of(9, 30),
        LocalTime.of(10, 45),
        "Flight to Paris",
        "pink",
        2,
    ),
    Event(
        LocalTime.of(10, 0),
        LocalTime.of(12, 0),
        "Meeting with design team at Disney",
        "gray",
        5,
    ),
)

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

private val csvFormat = CSVFormat.DEFAULT.builder()
    .setHeader("Day", "Start", "End", "Title", "Colour")
    .setSkipHeaderRecord(true)
    .get()

fun Routing.upload(repository: Repository) = post("/upload") {
    val file = File.createTempFile("aaa", "csv")

    val multipart = call.receiveMultipart()
    val part = multipart.readPart() ?: return@post call.respond(HttpStatusCode.BadRequest)
    if (part !is PartData.FileItem) return@post call.respond(HttpStatusCode.BadRequest)
    part.provider().copyAndClose(file.writeChannel())

    val parser = csvFormat.parse(file.reader())
    val events = parser.records.map {
        Event(
            day = it.get("Day").let {
                when (it) {
                    "Monday" -> 0
                    "Tuesday" -> 1
                    "Wednesday" -> 2
                    "Thursday" -> 3
                    "Friday" -> 4
                    "Saturday" -> 4
                    "Sunday" -> 4
                    else -> 0
                }
            },
            startTime = it.get("Start").let { LocalTime.parse(it) },
            endTime = it.get("End").let { LocalTime.parse(it) },
            title = it.get("Title"),
            colour = it.get("Colour"),
        )
    }
    val id = UUID.randomUUID().toString()
    repository.save(id, Timetable(events))
    call.response.header("HX-Redirect", "/timetable/$id")
    call.respond(HttpStatusCode.OK)
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
