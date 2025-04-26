package view.components

import kotlinx.html.FlowContent
import kotlinx.html.a
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.h3
import kotlinx.html.h4
import kotlinx.html.onClick
import kotlinx.html.p
import kotlinx.html.svg
import path

fun FlowContent.error(title: String, subtitle: String) = div {
    classes = setOf("rounded-md", "bg-red-50", "p-4", "absolute", "top-4", "w-full")
    onClick = "this.remove()"
    div {
        classes = setOf("flex")
        div {
            classes = setOf("shrink-0")
            svg {
                classes = setOf("size-5", "text-red-400")
                attributes["viewbox"] = "0 0 20 20"
                attributes["fill"] = "currentColor"
                attributes["aria-hidden"] = "true"
                attributes["data-slot"] = "icon"
                path {
                    attributes["fill-rule"] = "evenodd"
                    attributes["d"] =
                        "M10 18a8 8 0 1 0 0-16 8 8 0 0 0 0 16ZM8.28 7.22a.75.75 0 0 0-1.06 1.06L8.94 10l-1.72 1.72a.75.75 0 1 0 1.06 1.06L10 11.06l1.72 1.72a.75.75 0 1 0 1.06-1.06L11.06 10l1.72-1.72a.75.75 0 0 0-1.06-1.06L10 8.94 8.28 7.22Z"
                    attributes["clip-rule"] = "evenodd"
                }
            }
        }
        div {
            classes = setOf("ml-3", "text-left")
            h3 {
                classes = setOf("text-sm", "font-medium", "text-red-800")
                +title
            }
            div {
                classes = setOf("mt-2", "text-sm", "text-red-700")
                h4 {
                    +subtitle
                }
            }
        }
    }
}


