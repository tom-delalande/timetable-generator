package view

import kotlinx.html.ButtonType
import kotlinx.html.FlowContent
import kotlinx.html.InputType
import kotlinx.html.a
import kotlinx.html.button
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.label
import kotlinx.html.p
import kotlinx.html.span
import kotlinx.html.svg
import path

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
                +"Upload a CSV"
            }
        }
        form {
            div {
                classes = setOf("space-y-12")
                div {
                    classes = setOf("border-b", "border-gray-900/10", "pb-12")
                    div {
                        classes = setOf("mt-10", "grid", "grid-cols-1", "gap-x-6", "gap-y-8", "sm:grid-cols-6")
                        div {
                            classes = setOf("col-span-full")
                            label {
                                htmlFor = "cover-photo"
                                classes = setOf("block", "text-sm/6", "font-medium", "text-gray-900")
                                +"Upload Timetable"
                            }
                            div {
                                classes = setOf(
                                    "mt-2",
                                    "flex",
                                    "justify-center",
                                    "rounded-lg",
                                    "border",
                                    "border-dashed",
                                    "border-gray-900/25",
                                    "px-6",
                                    "py-10"
                                )
                                div {
                                    classes = setOf("text-center")
                                    svg {
                                        classes = setOf("mx-auto", "size-12", "text-gray-300")
                                        attributes["viewbox"] = "0 0 24 24"
                                        attributes["fill"] = "currentColor"
                                        attributes["aria-hidden"] = "true"
                                        attributes["data-slot"] = "icon"
                                        path {
                                            attributes["fill-rule"] = "evenodd"
                                            attributes["d"] =
                                                "M1.5 6a2.25 2.25 0 0 1 2.25-2.25h16.5A2.25 2.25 0 0 1 22.5 6v12a2.25 2.25 0 0 1-2.25 2.25H3.75A2.25 2.25 0 0 1 1.5 18V6ZM3 16.06V18c0 .414.336.75.75.75h16.5A.75.75 0 0 0 21 18v-1.94l-2.69-2.689a1.5 1.5 0 0 0-2.12 0l-.88.879.97.97a.75.75 0 1 1-1.06 1.06l-5.16-5.159a1.5 1.5 0 0 0-2.12 0L3 16.061Zm10.125-7.81a1.125 1.125 0 1 1 2.25 0 1.125 1.125 0 0 1-2.25 0Z"
                                            attributes["clip-rule"] = "evenodd"
                                        }
                                    }
                                    div {
                                        classes = setOf("mt-4", "flex", "text-sm/6", "text-gray-600")
                                        label {
                                            htmlFor = "file-upload"
                                            classes = setOf(
                                                "relative",
                                                "cursor-pointer",
                                                "rounded-md",
                                                "bg-white",
                                                "font-semibold",
                                                "text-indigo-600",
                                                "focus-within:outline-none",
                                                "focus-within:ring-2",
                                                "focus-within:ring-indigo-600",
                                                "focus-within:ring-offset-2",
                                                "hover:text-indigo-500"
                                            )
                                            span {
                                                +"Upload a file"
                                            }
                                            input {
                                                id = "file-upload"
                                                attributes["name"] = "file-upload"
                                                type = InputType.file
                                                classes = setOf("sr-only")
                                            }
                                        }
                                        p {
                                            classes = setOf("pl-1")
                                            +"or drag and drop"
                                        }
                                    }
                                    p {
                                        classes = setOf("text-xs/5", "text-gray-600")
                                        +"CSV up to 10MB"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        timetable()
    }
}
