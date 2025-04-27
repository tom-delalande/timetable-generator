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
                +"Lorem ipsum dolor sit amet consect adipisicing elit. Possimus magnam voluptatum cupiditate veritatis in accusamus quisquam."
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
                            +"Non quo aperiam repellendus quas est est. Eos aut dolore aut ut sit nesciunt. Ex tempora quia. Sit nobis consequatur dolores incidunt."
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
                                        "M16.023 9.348h4.992v-.001M2.985 19.644v-4.992m0 0h4.992m-4.993 0 3.181 3.183a8.25 8.25 0 0 0 13.803-3.7M4.031 9.865a8.25 8.25 0 0 1 13.803-3.7l3.181 3.182m0-4.991v4.99"
                                }
                            }
                        }
                        +"Interactable"
                    }
                    div {
                        classes = setOf("mt-1", "flex", "flex-auto", "flex-col", "text-base/7", "text-gray-600")
                        p {
                            classes = setOf("flex-auto")
                            +"Vero eum voluptatem aliquid nostrum voluptatem. Vitae esse natus. Earum nihil deserunt eos quasi cupiditate. A inventore et molestiae natus."
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
                            +"Et quod quaerat dolorem quaerat architecto aliquam accusantium. Ex adipisci et doloremque autem quia quam. Quis eos molestiae at iure impedit."
                        }
                    }
                }
            }
        }
    }
}
