package app

import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.html.respondHtml
import io.ktor.server.netty.Netty
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import java.time.LocalTime
import kotlinx.html.FlowContent
import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.h2
import kotlinx.html.head
import kotlinx.html.id
import kotlinx.html.script
import kotlinx.html.title
import view.page

fun main() {
    embeddedServer(Netty, port = 9090) {
        endpoints()
    }.start(wait = true)
}

fun Application.endpoints() {
    routing {
        index()
    }
}

data class Event(
    val startTime: LocalTime,
    val endTime: LocalTime,
    val title: String,
)

val event = listOf<Event>()

val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

fun Routing.index() = get("/") {
    call.respondHtml {
        index {
            page()
        }
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
        script { src = "/passkeys"; defer = true }
    }
    body {
        div {
            id = "content"
            page()
        }
    }
}
