package view.sections

import kotlinx.html.FlowContent
import kotlinx.html.a
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.footer
import kotlinx.html.p
import kotlinx.html.span
import kotlinx.html.svg
import path

fun FlowContent.footer() = footer {
    classes = setOf("bg-gray-900")
    div {
        classes = setOf(
            "mx-auto",
            "max-w-7xl",
            "px-6",
            "py-12",
            "md:flex",
            "md:items-center",
            "md:justify-between",
            "lg:px-8"
        )
        div {
            classes = setOf("flex", "justify-center", "gap-x-6", "md:order-2")
            a {
                href = "mailto:support@tinyclub.io"
                classes = setOf("text-gray-400", "hover:text-gray-300")
                span {
                    classes = setOf("sr-only")
                    +"Email"
                }
                svg {
                    attributes["fill"] = "none"
                    attributes["viewbox"] = "0 0 24 24"
                    attributes["stroke-width"] = "1.5"
                    attributes["stroke"] = "currentColor"
                    classes = setOf("size-6", "text-white")
                    path {
                        attributes["stroke-linecap"] = "round"
                        attributes["stroke-linejoin"] = "round"
                        attributes["d"] = "M21.75 6.75v10.5a2.25 2.25 0 0 1-2.25 2.25h-15a2.25 2.25 0 0 1-2.25-2.25V6.75m19.5 0A2.25 2.25 0 0 0 19.5 4.5h-15a2.25 2.25 0 0 0-2.25 2.25m19.5 0v.243a2.25 2.25 0 0 1-1.07 1.916l-7.5 4.615a2.25 2.25 0 0 1-2.36 0L3.32 8.91a2.25 2.25 0 0 1-1.07-1.916V6.75"
                    }
                }

            }
        }
        p {
            classes = setOf("mt-8", "text-center", "text-sm/6", "text-gray-400", "md:order-1", "md:mt-0")
            +"©"
            +"2024 TinyClub, Inc. All rights reserved."
        }
    }
}
