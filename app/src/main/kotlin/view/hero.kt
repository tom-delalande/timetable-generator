import kotlinx.html.FlowContent
import kotlinx.html.InputType
import kotlinx.html.a
import kotlinx.html.button
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.header
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.onChange
import kotlinx.html.onClick
import kotlinx.html.p
import kotlinx.html.span

fun FlowContent.hero() = div {
    div {
        classes = setOf("bg-white")
        header {
            classes = setOf("absolute", "inset-x-0", "top-0", "z-50")
        }
        div {
            classes = setOf("relative", "isolate", "px-6", "pt-14", "lg:px-8")
            div {
                classes = setOf(
                    "absolute",
                    "inset-x-0",
                    "-top-40",
                    "-z-10",
                    "transform-gpu",
                    "overflow-hidden",
                    "blur-3xl",
                    "sm:-top-80"
                )
                attributes["aria-hidden"] = "true"
                div {
                    classes = setOf(
                        "relative",
                        "left-[calc(50%-11rem)]",
                        "aspect-1155/678",
                        "w-[36.125rem]",
                        "-translate-x-1/2",
                        "rotate-[30deg]",
                        "bg-linear-to-tr",
                        "from-[#ff80b5]",
                        "to-[#9089fc]",
                        "opacity-30",
                        "sm:left-[calc(50%-30rem)]",
                        "sm:w-[72.1875rem]"
                    )
                    attributes["style"] =
                        "clip-path: polygon(74.1% 44.1%, 100% 61.6%, 97.5% 26.9%, 85.5% 0.1%, 80.7% 2%, 72.5% 32.5%, 60.2% 62.4%, 52.4% 68.1%, 47.5% 58.3%, 45.2% 34.5%, 27.5% 76.7%, 0.1% 64.9%, 17.9% 100%, 27.6% 76.8%, 76.1% 97.7%, 74.1% 44.1%)"
                }
            }
            div {
                classes = setOf("mx-auto", "max-w-2xl", "py-32", "sm:py-48", "lg:py-32")
                div {
                    classes = setOf("text-center")
                    h1 {
                        classes = setOf(
                            "text-5xl",
                            "font-semibold",
                            "tracking-tight",
                            "text-balance",
                            "text-gray-900",
                            "sm:text-7xl"
                        )
                        +"Beautiful timetable from a CSV file"
                    }
                    p {
                        classes =
                            setOf("mt-8", "text-lg", "font-medium", "text-pretty", "text-gray-500", "sm:text-xl/8")
                        +"Export directly as an image for free. Or use it interactively and updatable as a dedicated page or component on your website for $15 AUD per month"
                    }
                    div {
                        classes = setOf("mt-10", "flex", "items-center", "justify-center", "gap-x-6")
                        form {
                            attributes["hx-encoding"] = "multipart/form-data"
                            attributes["hx-post"] = "/upload"
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
            }
            div {
                classes = setOf(
                    "absolute",
                    "inset-x-0",
                    "top-[calc(100%-13rem)]",
                    "-z-10",
                    "transform-gpu",
                    "overflow-hidden",
                    "blur-3xl",
                    "sm:top-[calc(100%-30rem)]"
                )
                attributes["aria-hidden"] = "true"
                div {
                    classes = setOf(
                        "relative",
                        "left-[calc(50%+3rem)]",
                        "aspect-1155/678",
                        "w-[36.125rem]",
                        "-translate-x-1/2",
                        "bg-linear-to-tr",
                        "from-[#ff80b5]",
                        "to-[#9089fc]",
                        "opacity-30",
                        "sm:left-[calc(50%+36rem)]",
                        "sm:w-[72.1875rem]"
                    )
                    attributes["style"] =
                        "clip-path: polygon(74.1% 44.1%, 100% 61.6%, 97.5% 26.9%, 85.5% 0.1%, 80.7% 2%, 72.5% 32.5%, 60.2% 62.4%, 52.4% 68.1%, 47.5% 58.3%, 45.2% 34.5%, 27.5% 76.7%, 0.1% 64.9%, 17.9% 100%, 27.6% 76.8%, 76.1% 97.7%, 74.1% 44.1%)"
                }
            }
        }
    }
}