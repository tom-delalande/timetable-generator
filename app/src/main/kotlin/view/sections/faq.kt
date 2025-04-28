package view.sections

import kotlinx.html.FlowContent
import kotlinx.html.a
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.h2
import kotlinx.html.p

fun FlowContent.faq() = div {
    classes = setOf("bg-gray-100")
    div {
        classes = setOf("mx-auto", "max-w-7xl", "px-6", "py-24", "sm:pt-32", "lg:px-8", "lg:py-40")
        div {
            classes = setOf("lg:grid", "lg:grid-cols-12", "lg:gap-8")
            div {
                classes = setOf("lg:col-span-5")
                h2 {
                    classes = setOf(
                        "text-3xl",
                        "font-semibold",
                        "tracking-tight",
                        "text-pretty",
                        "text-gray-900",
                        "sm:text-4xl"
                    )
                    +"Frequently asked questions"
                }
                p {
                    classes = setOf("mt-4", "text-base/7", "text-pretty", "text-gray-600")
                    +"Can’t find the answer you’re looking for? Reach out to our "
                    a {
                        href = "mailto:support@tinyclub.io"
                        classes = setOf("font-semibold", "text-indigo-600", "hover:text-indigo-500")
                        +"customer support"
                    }
                    +" team."
                }
            }
            div {
                classes = setOf("mt-10", "lg:col-span-7", "lg:mt-0")
                div {
                    classes = setOf("space-y-10")
                    div {
                        div {
                            classes = setOf("text-base/7", "font-semibold", "text-gray-900")
                            +"How do I update this timetable?"
                        }
                        div {
                            classes = setOf("mt-2", "text-base/7", "text-gray-600")
                            +"You can come back to this URL at any time to upload a new version of the time table, which will instantly update all instances of the time table."
                        }
                    }
                    div {
                        div {
                            classes = setOf("text-base/7", "font-semibold", "text-gray-900")
                            +"How do I show the timetable on my website?"
                        }
                        div {
                            classes = setOf("mt-2", "text-base/7", "text-gray-600")
                            +"You can add the HTML snippet to embed the timetable directly into your website, or link to the url provided which will open the timetable in a new page."
                        }
                    }
                    div {
                        div {
                            classes = setOf("text-base/7", "font-semibold", "text-gray-900")
                            +"How do I cancel my subscription?"
                        }
                        div {
                            classes = setOf("mt-2", "text-base/7", "text-gray-600")
                            +"You can cancel immediately at any time. No questions asked"
                        }
                    }
                    div {
                        div {
                            classes = setOf("text-base/7", "font-semibold", "text-gray-900")
                            +"I have a question, feature request or bug report"
                        }
                        div {
                            classes = setOf("mt-2", "text-base/7", "text-gray-600")
                            +"Send me an email at "
                            a {
                                href = "mailto:support@tinyclub.io"
                                classes = setOf("font-semibold", "text-indigo-600", "hover:text-indigo-500")
                                +"support@tinyclub.io"
                            }
                            +" and I'll get back to you as quickly as possible"
                        }
                    }
                }
            }
        }
    }
}
