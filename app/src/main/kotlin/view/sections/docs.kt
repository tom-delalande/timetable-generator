package view.sections

import app.Environment
import java.util.UUID
import kotlinx.html.FlowContent
import kotlinx.html.InputType
import kotlinx.html.a
import kotlinx.html.button
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h3
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.label
import kotlinx.html.onChange
import kotlinx.html.onClick
import kotlinx.html.p
import kotlinx.html.span
import kotlinx.html.svg
import path
import repository.Timetable

fun FlowContent.docs(timetable: Timetable, subscribed: Boolean) = div {
    classes = setOf("bg-white")
    div {
        classes = setOf("px-6", "py-4", "sm:px-6", "sm:py-8", "lg:px-8")
        div {
            classes = setOf("mx-auto", "max-w-2xl", "text-center")
            div {
                classes = setOf("mt-10", "flex", "flex-col", "items-start", "justify-center", "gap-6")
                copyLink(timetable.publicId)
                codeExample(timetable.publicId)
            }
            div {
                classes = setOf("py-4")
                if (!subscribed) {
                    warning(timetable.privateId)
                    upload(timetable.privateId)
                } else {
                    success(timetable.privateId)
                    upload(timetable.privateId)
                }
            }
        }
    }
}

private fun FlowContent.downloadImage(id: String) = div {
    label {
        classes = setOf("block", "text-sm/6", "font-medium", "text-gray-900", "text-left")
        +"Download"
    }
    div {
        classes = setOf(
            "mt-2",
        )
        div {
            a {
                href = "/timetable/$id/timetable.png"

                classes = setOf("flex", "justify-center")

                div {
                    classes = setOf(
                        "rounded-full",
                        "bg-indigo-600",
                        "p-2",
                        "text-white",
                        "shadow-xs",
                        "hover:bg-indigo-500",
                        "focus-visible:outline-2",
                        "focus-visible:outline-offset-2",
                        "focus-visible:outline-indigo-600"
                    )
                    svg {
                        attributes["fill"] = "none"
                        attributes["viewbox"] = "0 0 24 24"
                        attributes["stroke-width"] = "1.5"
                        attributes["stroke"] = "currentColor"
                        classes = setOf("size-6", "text-white")
                        path {
                            attributes["stroke-linecap"] = "round"
                            attributes["stroke-linejoin"] = "round"
                            attributes["d"] =
                                "M3 16.5v2.25A2.25 2.25 0 0 0 5.25 21h13.5A2.25 2.25 0 0 0 21 18.75V16.5M16.5 12 12 16.5m0 0L7.5 12m4.5 4.5V3"
                        }
                    }
                }
            }

        }
    }
}

private fun FlowContent.upload(privateId: UUID) = div {
    label {
        classes = setOf("block", "text-sm/6", "font-medium", "text-gray-900", "text-left")
        +"Update"
    }
    div {
        classes = setOf("flex", "items-center", "justify-left", "gap-x-6", "mt-2")
        form {
            attributes["hx-encoding"] = "multipart/form-data"
            attributes["hx-post"] = "/upload?timetable=$privateId"
            attributes["hx-target"] = "#error"
            attributes["hx-swap"] = "innerHtml"

            input {
                id = "file-upload"
                type = InputType.file
                classes = setOf("hidden")
                onChange = "this.form.requestSubmit()"
                name = "file"
            }
        }

        button {
            onClick = "document.getElementById('file-upload').click()"
            classes = setOf(
                "rounded-md",
                "bg-indigo-600",
                "px-3.5",
                "py-2.5",
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
        a {
            href = "/timetable-converter-example.csv"
            classes = setOf("text-sm/6", "font-semibold", "text-gray-900")
            +"Download Template"
            span {
                attributes["aria-hidden"] = "true"
                +"→"
            }
        }
    }
    div {
        id = "error"
        classes = setOf("relative", "h-0")
    }
}

private fun FlowContent.copyLink(id: UUID) = div {
    label {
        classes = setOf("block", "text-sm/6", "font-medium", "text-gray-900", "text-left")
        +"URL"
    }
    div {
        classes = setOf(
            "mt-2",
            "flex",
            "gap-2",
        )
        div {
            classes = setOf(
                "flex",
                "items-center",
                "rounded-md",
                "bg-white",
                "pl-3",
                "outline-1",
                "outline",
                "-outline-offset-1",
                "outline-gray-300",
                "focus-within:outline-2",
                "focus-within:-outline-offset-2",
                "focus-within:outline-indigo-600"
            )
            div {
                classes = setOf(
                    "block",
                    "min-w-0",
                    "grow",
                    "py-1.5",
                    "pr-3",
                    "pl-1",
                    "text-base",
                    "text-gray-500",
                    "placeholder:text-gray-400",
                    "focus:outline-none",
                    "sm:text-sm/6"
                )
                +"${Environment.baseUrl}/timetable/$id"
            }
        }
        a {
            href = "/timetable/$id"
            target = "_blank"
            classes = setOf("flex", "justify-center")
            div {
                classes = setOf(
                    "rounded-full",
                    "bg-indigo-600",
                    "p-2",
                    "text-white",
                    "shadow-xs",
                    "hover:bg-indigo-500",
                    "focus-visible:outline-2",
                    "focus-visible:outline-offset-2",
                    "focus-visible:outline-indigo-600"
                )
                svg {
                    attributes["fill"] = "none"
                    attributes["viewbox"] = "0 0 24 24"
                    attributes["stroke-width"] = "1.5"
                    attributes["stroke"] = "currentColor"
                    classes = setOf("size-6", "text-white")
                    path {
                        attributes["stroke-linecap"] = "round"
                        attributes["stroke-linejoin"] = "round"
                        attributes["d"] =
                            "M13.19 8.688a4.5 4.5 0 0 1 1.242 7.244l-4.5 4.5a4.5 4.5 0 0 1-6.364-6.364l1.757-1.757m13.35-.622 1.757-1.757a4.5 4.5 0 0 0-6.364-6.364l-4.5 4.5a4.5 4.5 0 0 0 1.242 7.244"
                    }
                }

            }
        }
    }
}

private fun FlowContent.codeExample(id: UUID) = div {
    label {
        classes = setOf("block", "text-sm/6", "font-medium", "text-gray-900", "text-left")
        +"Embedded"
    }
    div {
        classes = setOf(
            "mt-2",
        )
        div {
            classes = setOf(
                "flex",
                "items-center",
                "rounded-md",
                "bg-white",
                "pl-3",
                "outline-1",
                "outline",
                "-outline-offset-1",
                "outline-gray-300",
                "focus-within:outline-2",
                "focus-within:-outline-offset-2",
                "focus-within:outline-indigo-600"
            )
            div {
                classes = setOf(
                    "block",
                    "min-w-0",
                    "grow",
                    "py-1.5",
                    "pr-3",
                    "pl-1",
                    "text-base",
                    "text-gray-500",
                    "placeholder:text-gray-400",
                    "focus:outline-none",
                    "sm:text-sm/6",
                    "text-left",
                )
                p {
                    +"<iframe src=\"${Environment.baseUrl}/timetable/$id?embedded=true\" title=\"Timetable\"></iframe>"
                }
            }
        }
    }
}

fun FlowContent.warning(id: UUID) = div {
    classes = setOf("rounded-md", "bg-yellow-50", "p-4", "my-4")
    div {
        classes = setOf("flex")
        div {
            classes = setOf("shrink-0")
            svg {
                classes = setOf("size-5", "text-yellow-400")
                attributes["viewbox"] = "0 0 20 20"
                attributes["fill"] = "currentColor"
                attributes["aria-hidden"] = "true"
                attributes["data-slot"] = "icon"
                path {
                    attributes["fill-rule"] = "evenodd"
                    attributes["d"] =
                        "M8.485 2.495c.673-1.167 2.357-1.167 3.03 0l6.28 10.875c.673 1.167-.17 2.625-1.516 2.625H3.72c-1.347 0-2.189-1.458-1.515-2.625L8.485 2.495ZM10 5a.75.75 0 0 1 .75.75v3.5a.75.75 0 0 1-1.5 0v-3.5A.75.75 0 0 1 10 5Zm0 9a1 1 0 1 0 0-2 1 1 0 0 0 0 2Z"
                    attributes["clip-rule"] = "evenodd"
                }
            }
        }
        div {
            classes = setOf("ml-3", "text-left")
            h3 {
                classes = setOf("text-sm", "font-medium", "text-yellow-800")
                +"Warning"
            }
            div {
                classes = setOf("mt-2", "text-sm", "text-yellow-700")
                p {
                    +"This timetable will only remain available for 1 day. "
                    a {
                        href = "/payments/purchase/timetable/$id"
                        classes = setOf("font-medium text-yellow-700 underline hover:text-yellow-600")
                        +"Upgrade to keep using it for $15 AUD per month"
                    }
                }
            }
        }
    }
}

fun FlowContent.success(id: UUID) = div {
    classes = setOf("rounded-md", "bg-green-50", "p-4")
    div {
        classes = setOf("flex")
        div {
            classes = setOf("shrink-0")
            svg {
                classes = setOf("size-5", "text-green-400")
                attributes["viewbox"] = "0 0 20 20"
                attributes["fill"] = "currentColor"
                attributes["aria-hidden"] = "true"
                attributes["data-slot"] = "icon"
                path {
                    attributes["fill-rule"] = "evenodd"
                    attributes["d"] =
                        "M10 18a8 8 0 1 0 0-16 8 8 0 0 0 0 16Zm3.857-9.809a.75.75 0 0 0-1.214-.882l-3.483 4.79-1.88-1.88a.75.75 0 1 0-1.06 1.061l2.5 2.5a.75.75 0 0 0 1.137-.089l4-5.5Z"
                    attributes["clip-rule"] = "evenodd"
                }
            }
        }
        div {
            classes = setOf("ml-3", "text-left")
            h3 {
                classes = setOf("text-sm", "font-medium", "text-green-800")
                +"Successfully subscribed"
            }
            div {
                classes = setOf("mt-2", "text-sm", "text-green-700")
                p {
                    +"You can edit the timetable at any time by coming back to this URL"
                }
            }
        }
    }
}
