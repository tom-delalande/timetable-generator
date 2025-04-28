package view.sections

import kotlinx.html.FlowContent
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.dl
import kotlinx.html.h2
import kotlinx.html.p
import kotlinx.html.svg
import path

fun FlowContent.features() = div {
    classes = setOf("bg-white", "py-24", "sm:py-32")
    div {
        classes = setOf("mx-auto", "max-w-7xl", "px-6", "lg:px-8")
        div {
            classes = setOf("mx-auto", "max-w-2xl", "lg:mx-0")
            h2 {
                classes =
                    setOf("text-4xl", "font-semibold", "tracking-tight", "text-pretty", "text-gray-900", "sm:text-5xl")
                +"Keep track of all that's happening"
            }
            p {
                classes = setOf("mt-6", "text-lg/8", "text-gray-600")
                +"Create a beautiful timetable for your gym or business, update it in real time and share it with your customers."
            }
        }
        div {
            classes = setOf("mx-auto", "mt-16", "max-w-2xl", "sm:mt-20", "lg:mt-24", "lg:max-w-none")
            dl {
                classes =
                    setOf("grid", "max-w-xl", "grid-cols-1", "gap-x-8", "gap-y-16", "lg:max-w-none", "lg:grid-cols-3")
                div {
                    classes = setOf("flex", "flex-col")
                    div {
                        classes = setOf("text-base/7", "font-semibold", "text-gray-900")
                        div {
                            classes = setOf(
                                "mb-6",
                                "flex",
                                "size-10",
                                "items-center",
                                "justify-center",
                                "rounded-lg",
                                "bg-indigo-600"
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
                                        "M16.023 9.348h4.992v-.001M2.985 19.644v-4.992m0 0h4.992m-4.993 0 3.181 3.183a8.25 8.25 0 0 0 13.803-3.7M4.031 9.865a8.25 8.25 0 0 1 13.803-3.7l3.181 3.182m0-4.991v4.99"
                                }
                            }
                        }
                        +"Update in real time"
                    }
                    div {
                        classes = setOf("mt-1", "flex", "flex-auto", "flex-col", "text-base/7", "text-gray-600")
                        p {
                            classes = setOf("flex-auto")
                            +"Update the timetable instantly without needing to update your website, a frictionless way to communicate with your customers and reduce confusion"
                        }
                    }
                }
                div {
                    classes = setOf("flex", "flex-col")
                    div {
                        classes = setOf("text-base/7", "font-semibold", "text-gray-900")
                        div {
                            classes = setOf(
                                "mb-6",
                                "flex",
                                "size-10",
                                "items-center",
                                "justify-center",
                                "rounded-lg",
                                "bg-indigo-600"
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
                                        "M9.813 15.904 9 18.75l-.813-2.846a4.5 4.5 0 0 0-3.09-3.09L2.25 12l2.846-.813a4.5 4.5 0 0 0 3.09-3.09L9 5.25l.813 2.846a4.5 4.5 0 0 0 3.09 3.09L15.75 12l-2.846.813a4.5 4.5 0 0 0-3.09 3.09ZM18.259 8.715 18 9.75l-.259-1.035a3.375 3.375 0 0 0-2.455-2.456L14.25 6l1.036-.259a3.375 3.375 0 0 0 2.455-2.456L18 2.25l.259 1.035a3.375 3.375 0 0 0 2.456 2.456L21.75 6l-1.035.259a3.375 3.375 0 0 0-2.456 2.456ZM16.894 20.567 16.5 21.75l-.394-1.183a2.25 2.25 0 0 0-1.423-1.423L13.5 18.75l1.183-.394a2.25 2.25 0 0 0 1.423-1.423l.394-1.183.394 1.183a2.25 2.25 0 0 0 1.423 1.423l1.183.394-1.183.394a2.25 2.25 0 0 0-1.423 1.423Z"
                                }
                            }
                        }
                        +"Interactable"
                    }
                    div {
                        classes = setOf("mt-1", "flex", "flex-auto", "flex-col", "text-base/7", "text-gray-600")
                        p {
                            classes = setOf("flex-auto")
                            +"More than just a static page. This is an interactive webpage that shows more information than a simple image."
                        }
                    }
                }
                div {
                    classes = setOf("flex", "flex-col")
                    div {
                        classes = setOf("text-base/7", "font-semibold", "text-gray-900")
                        div {
                            classes = setOf(
                                "mb-6",
                                "flex",
                                "size-10",
                                "items-center",
                                "justify-center",
                                "rounded-lg",
                                "bg-indigo-600"
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
                                        "M10.5 1.5H8.25A2.25 2.25 0 0 0 6 3.75v16.5a2.25 2.25 0 0 0 2.25 2.25h7.5A2.25 2.25 0 0 0 18 20.25V3.75a2.25 2.25 0 0 0-2.25-2.25H13.5m-3 0V3h3V1.5m-3 0h3m-3 18.75h3"
                                }
                            }

                        }
                        +"Mobile Friendly"
                    }
                    div {
                        classes = setOf("mt-1", "flex", "flex-auto", "flex-col", "text-base/7", "text-gray-600")
                        p {
                            classes = setOf("flex-auto")
                            +"Render on all devices, including mobile."
                        }
                    }
                }
            }
        }
    }
}
