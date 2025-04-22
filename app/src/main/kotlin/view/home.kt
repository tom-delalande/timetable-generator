package view

import app.exampleEvents
import kotlinx.html.ButtonType
import kotlinx.html.FlowContent
import kotlinx.html.FormMethod
import kotlinx.html.InputType
import kotlinx.html.a
import kotlinx.html.button
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.input
import repository.Timetable

fun FlowContent.home() = div {
    classes = setOf("mx-auto max-w-7xl px-4 sm:px-6 lg:px-8")
    div {
        classes = setOf("mx-auto max-w-3xl")
        div {
            classes = setOf("py-6", "flex", "justify-center", "align-center", "gap-4")
            a {
                href = "/timetable-converter-example.csv"
                downLoad = ""
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
                +"Download Example"
            }
        }
        form {
            attributes["hx-encoding"] = "multipart/form-data"
            attributes["hx-post"] = "/upload"

            div {
                classes = setOf("space-y-12")
                div {
                    classes = setOf("border-b", "border-gray-900/10", "pb-12")
                    div {
                        classes = setOf("flex", "flex-col", "items-center", "gap-2")
                        input {
                            type = InputType.file
                            name = "file"
                        }
                        div {
                            button {
                                type = ButtonType.submit
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
                                +"Upload a CSV"
                            }
                        }
                    }
                }
            }
        }
        timetable(Timetable(exampleEvents))
    }
}
