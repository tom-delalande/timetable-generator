package view

import kotlinx.html.ButtonType
import kotlinx.html.FlowContent
import kotlinx.html.button
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.p

fun FlowContent.timetableDocs(id: String) = div {
    classes = setOf("flex", "flex-col")
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
