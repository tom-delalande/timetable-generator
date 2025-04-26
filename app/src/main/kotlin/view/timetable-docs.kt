package view

import java.util.UUID
import kotlinx.html.ButtonType
import kotlinx.html.FlowContent
import kotlinx.html.a
import kotlinx.html.button
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.p

fun FlowContent.timetableDocs(id: UUID) = div {
    classes = setOf("flex", "flex-col", "gap-4")
    div {
        classes = setOf("border-b", "border-gray-900/10", "py-6")
        button {
            type = ButtonType.button
            classes = setOf(
                "rounded-md",
                "bg-indigo-600",
                "px-3",
                "py-2",
                "text-sm",
                "font-semibold",
                "text-white",
                "shadow-xs",
                "hover:bg-indigo-500",
                "focus-visible:outline-2",
                "focus-visible:outline-offset-2",
                "focus-visible:outline-indigo-600"
            )
            +"Download as PDF"
        }
    }
    div {
        a {
            href = "/payments/purchase/timetable/$id"
            classes = setOf(
                "rounded-md",
                "bg-indigo-600",
                "px-3",
                "py-2",
                "text-sm",
                "font-semibold",
                "text-white",
                "shadow-xs",
                "hover:bg-indigo-500",
                "focus-visible:outline-2",
                "focus-visible:outline-offset-2",
                "focus-visible:outline-indigo-600"
            )
            +"Purchase"
        }
    }
    div {
        p {
            +"""
                <iframe src="/" />
            """.trimIndent()
        }
    }
    div {
        p {
            +"https://example.com/"
        }
    }
}
