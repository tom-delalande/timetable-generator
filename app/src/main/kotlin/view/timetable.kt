@file:UseSerializers(
    LocalTimeSerializer::class,
)

package view

import app.LocalTimeSerializer
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlinx.html.ButtonType
import kotlinx.html.FlowContent
import kotlinx.html.a
import kotlinx.html.button
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.header
import kotlinx.html.id
import kotlinx.html.li
import kotlinx.html.ol
import kotlinx.html.p
import kotlinx.html.role
import kotlinx.html.span
import kotlinx.html.svg
import kotlinx.html.time
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import repository.Timetable

@Serializable
data class Event(
    val startTime: LocalTime,
    val endTime: LocalTime,
    val title: String,
    val colour: String,
    val day: Int,
)

data class Collisions(
    val num: Int,
    val offset: Int,
)

data class RelativeEvent(
    val event: Event,
    val order: Int,
    val halved: Collisions,
    val indent: Int,
)

fun FlowContent.timetable(timetable: Timetable) = div {
    val times = (0..23).map { LocalTime.of(it, 0) }

    val events = timetable.events

    val relativeEvents = events.sortedWith(compareBy({ it.day }, { it.startTime }))
        .mapIndexed { index, event ->
            val indent =
                events.count { it.startTime < event.startTime && event.startTime < it.endTime && it.day == event.day }
            val collisions = events.filter { other ->
                (event.startTime >= other.startTime && event.startTime <= other.startTime.plusMinutes(30) ||
                        other.startTime >= event.startTime && other.startTime <= event.startTime.plusMinutes(30)) &&
                        event.day == other.day
            }.sortedByDescending { Duration.between(it.startTime, it.endTime) }

            val offset = collisions.indexOf(event)
            val num = collisions.filter { it.endTime >= event.endTime }.size

            RelativeEvent(
                event,
                index,
                Collisions(num, offset),
                indent,
            )
        }

    val min = events.minOf { it.startTime }.truncatedTo(ChronoUnit.HOURS)
    val max = events.maxOf { it.endTime }
    val filteredTimes = times.filter { it in min..max }
    val factor = filteredTimes.size * 2

    div {
        classes = setOf("flex", "h-full", "flex-col")
        header {
            classes = setOf(
                "flex",
                "flex-none",
                "items-center",
                "justify-between",
                "border-b",
                "border-gray-200",
                "px-6",
                "py-4",
            )
            // ^ hidden in-case I want a title later
            if (timetable.title != null) {
                h1 {
                    classes = setOf("text-base", "font-semibold", "text-gray-900")
                    //                                                              ^ hidden in-case I want a title later
                    time {
                        attributes["datetime"] = "2022-01"
                        +timetable.title
                    }
                }
            }
            div {
                classes = setOf("flex", "items-center", "hidden")
                //                                      ^ hidden in-case I want to show this button later
                div {
                    classes = setOf("hidden", "md:ml-4", "md:flex", "md:items-center")
                    div {
                        classes = setOf("ml-6", "h-6", "w-px", "bg-gray-300")
                    }
                    button {
                        type = ButtonType.button
                        classes = setOf(
                            "ml-6",
                            "rounded-md",
                            "bg-indigo-600",
                            "px-3",
                            "py-2",
                            "text-sm",
                            "font-semibold",
                            "text-white",
                            "shadow-sm",
                            "hover:bg-indigo-500",
                            "focus-visible:outline",
                            "focus-visible:outline-2",
                            "focus-visible:outline-offset-2",
                            "focus-visible:outline-indigo-500"
                        )
                        +"Add event"
                    }
                }
                div {
                    classes = setOf("relative", "ml-6", "md:hidden")
                    button {
                        type = ButtonType.button
                        classes = setOf(
                            "-mx-2",
                            "flex",
                            "items-center",
                            "rounded-full",
                            "border",
                            "border-transparent",
                            "p-2",
                            "text-gray-400",
                            "hover:text-gray-500"
                        )
                        id = "menu-0-button"
                        attributes["aria-expanded"] = "false"
                        attributes["aria-haspopup"] = "true"
                        span {
                            classes = setOf("sr-only")
                            +"Open menu"
                        }
                        svg {
                            classes = setOf("size-5")
                            attributes["viewbox"] = "0 0 20 20"
                            attributes["fill"] = "currentColor"
                            attributes["aria-hidden"] = "true"
                            attributes["data-slot"] = "icon"
//                            path {
//                                attributes["d"] = "M3 10a1.5 1.5 0 1 1 3 0 1.5 1.5 0 0 1-3 0ZM8.5 10a1.5 1.5 0 1 1 3 0 1.5 1.5 0 0 1-3 0ZM15.5 8.5a1.5 1.5 0 1 0 0 3 1.5 1.5 0 0 0 0-3Z"
//                            }
                        }
                    }
                    div {
                        classes = setOf(
                            "absolute",
                            "right-0",
                            "z-10",
                            "mt-3",
                            "w-36",
                            "origin-top-right",
                            "divide-y",
                            "divide-gray-100",
                            "overflow-hidden",
                            "rounded-md",
                            "bg-white",
                            "shadow-lg",
                            "ring-1",
                            "ring-black/5",
                            "focus:outline-none"
                        )
                        role = "menu"
                        attributes["aria-orientation"] = "vertical"
                        attributes["aria-labelledby"] = "menu-0-button"
                        attributes["tabindex"] = "-1"
                        div {
                            classes = setOf("py-1")
                            role = "none"
                            a {
                                href = "#"
                                classes = setOf("block", "px-4", "py-2", "text-sm", "text-gray-700")
                                role = "menuitem"
                                attributes["tabindex"] = "-1"
                                id = "menu-0-item-0"
                                +"Create event"
                            }
                        }
                        div {
                            classes = setOf("py-1")
                            role = "none"
                            a {
                                href = "#"
                                classes = setOf("block", "px-4", "py-2", "text-sm", "text-gray-700")
                                role = "menuitem"
                                attributes["tabindex"] = "-1"
                                id = "menu-0-item-1"
                                +"Go to today"
                            }
                        }
                        div {
                            classes = setOf("py-1")
                            role = "none"
                            a {
                                href = "#"
                                classes = setOf("block", "px-4", "py-2", "text-sm", "text-gray-700")
                                role = "menuitem"
                                attributes["tabindex"] = "-1"
                                id = "menu-0-item-2"
                                +"Day view"
                            }
                            a {
                                href = "#"
                                classes = setOf("block", "px-4", "py-2", "text-sm", "text-gray-700")
                                role = "menuitem"
                                attributes["tabindex"] = "-1"
                                id = "menu-0-item-3"
                                +"Week view"
                            }
                            a {
                                href = "#"
                                classes = setOf("block", "px-4", "py-2", "text-sm", "text-gray-700")
                                role = "menuitem"
                                attributes["tabindex"] = "-1"
                                id = "menu-0-item-4"
                                +"Month view"
                            }
                            a {
                                href = "#"
                                classes = setOf("block", "px-4", "py-2", "text-sm", "text-gray-700")
                                role = "menuitem"
                                attributes["tabindex"] = "-1"
                                id = "menu-0-item-5"
                                +"Year view"
                            }
                        }
                    }
                }
            }
        }
        div {
            classes = setOf("isolate", "flex", "flex-auto", "flex-col", "overflow-auto", "bg-white")
            div {
                attributes["style"] = "width: 165%"
                classes = setOf("flex", "max-w-full", "flex-none", "flex-col", "sm:max-w-none", "md:max-w-full")
                div {
                    classes = setOf(
                        "sticky",
                        "top-0",
                        "z-30",
                        "flex-none",
                        "bg-white",
                        "shadow",
                        "ring-1",
                        "ring-black/5",
                        "sm:pr-8"
                    )
                    div {
                        classes = setOf(
//                            "sm:hidden",
//                            "grid",
                            "hidden",
                            "grid-cols-7",
                            "text-sm/6",
                            "text-gray-500",
                        )
                        button {
                            type = ButtonType.button
                            classes = setOf("flex", "flex-col", "items-center", "pb-2", "pt-2")
                            +"M"
                        }
                        button {
                            type = ButtonType.button
                            classes = setOf("flex", "flex-col", "items-center", "pb-2", "pt-2")
                            +"T"
                        }
                        button {
                            type = ButtonType.button
                            classes = setOf("flex", "flex-col", "items-center", "pb-2", "pt-2")
                            span {
                                classes = setOf(
                                    "flex",
                                    "size-8",
                                    "items-center",
                                    "justify-center",
                                    "rounded-full",
                                    "bg-indigo-600",
                                    "font-semibold",
                                    "text-white"
                                )
                                +"W"
                            }
                        }
                        button {
                            type = ButtonType.button
                            classes = setOf("flex", "flex-col", "items-center", "pb-2", "pt-2")
                            +"T"
                        }
                        button {
                            type = ButtonType.button
                            classes = setOf("flex", "flex-col", "items-center", "pb-2", "pt-2")
                            +"F"
                        }
                        button {
                            type = ButtonType.button
                            classes = setOf("flex", "flex-col", "items-center", "pb-2", "pt-2")
                            +"S"
                        }
                        button {
                            type = ButtonType.button
                            classes = setOf("flex", "flex-col", "items-center", "pb-2", "pt-2")
                            +"S"
                        }
                    }
                    div {
                        classes = setOf(
                            "-mr-px",
                            "grid",
//                            "sm:grid",
//                            "hidden",

                            "grid-cols-7",
                            "divide-x",
                            "divide-gray-100",
                            "border-r",
                            "border-gray-100",
                            "text-sm/6",
                            "text-gray-500",
                        )
                        div {
                            classes = setOf("col-end-1", "w-14")
                        }
                        div {
                            classes = setOf("flex", "items-center", "justify-center", "py-3")
                            span {
                                +"Mon"
                            }
                        }
                        div {
                            classes = setOf("flex", "items-center", "justify-center", "py-3")
                            span {
                                +"Tue"
                            }
                        }
                        div {
                            classes = setOf("flex", "items-center", "justify-center", "py-3")
                            span {
                                classes = setOf("flex", "items-baseline")
                                +"Wed"
                            }
                        }
                        div {
                            classes = setOf("flex", "items-center", "justify-center", "py-3")
                            span {
                                +"Thu"
                            }
                        }
                        div {
                            classes = setOf("flex", "items-center", "justify-center", "py-3")
                            span {
                                +"Fri"
                            }
                        }
                        div {
                            classes = setOf("flex", "items-center", "justify-center", "py-3")
                            span {
                                +"Sat"
                            }
                        }
                        div {
                            classes = setOf("flex", "items-center", "justify-center", "py-3")
                            span {
                                +"Sun"
                            }
                        }
                    }
                }
                div {
                    classes = setOf("flex", "flex-auto")
                    div {
                        classes = setOf(
                            "sticky",
                            "left-0",
                            "z-10",
                            "w-14",
                            "flex-none",
                            "bg-white",
                            "ring-1",
                            "ring-gray-100"
                        )
                    }
                    div {
                        classes = setOf("grid", "flex-auto", "grid-cols-1", "grid-rows-1")
                        div {
                            classes =
                                setOf("col-start-1", "col-end-2", "row-start-1", "grid", "divide-y", "divide-gray-100")
                            attributes["style"] = "grid-template-rows: repeat($factor, minmax(3.5rem, 1fr))"
                            div {
                                classes = setOf("row-end-1", "h-7")
                            }
                            filteredTimes.map {
                                div {
                                    div {
                                        classes = setOf(
                                            "sticky",
                                            "left-0",
                                            "z-20",
                                            "-ml-14",
                                            "-mt-2.5",
                                            "w-14",
                                            "pr-2",
                                            "text-right",
                                            "text-xs/5",
                                            "text-gray-400"
                                        )
                                        +it.format(DateTimeFormatter.ofPattern("ha")).uppercase()
                                    }
                                }
                                div {
                                }
                            }
                        }
                        div {
                            classes = setOf(
                                "col-start-1",
                                "col-end-2",
                                "row-start-1",
                                "hidden",
                                "grid-cols-7",
                                "grid-rows-1",
                                "divide-x",
                                "divide-gray-100",
                                "sm:grid",
                                "sm:grid-cols-7"
                            )
                            div {
                                classes = setOf("col-start-1", "row-span-full")
                            }
                            div {
                                classes = setOf("col-start-2", "row-span-full")
                            }
                            div {
                                classes = setOf("col-start-3", "row-span-full")
                            }
                            div {
                                classes = setOf("col-start-4", "row-span-full")
                            }
                            div {
                                classes = setOf("col-start-5", "row-span-full")
                            }
                            div {
                                classes = setOf("col-start-6", "row-span-full")
                            }
                            div {
                                classes = setOf("col-start-7", "row-span-full")
                            }
                            div {
                                classes = setOf("col-start-8", "row-span-full", "w-8")
                            }
                        }
                        ol {
                            classes = setOf(
                                "col-start-1",
                                "col-end-2",
                                "row-start-1",
                                "grid",
//                                "grid-cols-1",
//                                "sm:grid-cols-7",
                                "grid-cols-7",
                                "sm:pr-8"
                            )
                            attributes["style"] =
                                "grid-template-rows: 1.75rem repeat(${factor * 6}, minmax(0, 1fr)) auto"
                            relativeEvents.map { (event, index, collisions, inset) ->
                                val factor = 12
                                val gridRow =
                                    2 + (event.startTime.hour - filteredTimes.first().hour) * factor + (event.startTime.minute * factor / 60)
                                val span =
                                    (((event.endTime.hour.toFloat() + event.endTime.minute.toFloat() / 60) - (event.startTime.hour.toFloat() + event.startTime.minute.toFloat() / 60)) * factor).toInt()

                                li {
                                    classes = setOf(
                                        "relative",
                                        "mt-px",
                                        "flex",
                                        "sm:col-start-${event.day + 1}",
                                        "overflow-hidden",
                                        "hover:overflow-visible",
                                        "z-[$index]",
                                        "hover:z-[10000]",
                                        "w-1/${collisions.num}",
                                        "translate-x-[${collisions.offset * 100}%]",
                                        "hover:w-full",
                                        "hover:translate-x-[0]",
                                    )
                                    attributes["style"] = "grid-row: $gridRow / span $span"
                                    div {
                                        classes = setOf(
                                            "group",
                                            "absolute",
                                            "inset-${inset}",
                                            "end-1",
                                            "flex",
                                            "flex-col",
                                            "rounded-lg",
                                            "bg-${event.colour}-50",
                                            "p-2",
                                            "text-xs/5",
                                            "hover:bg-${event.colour}-100",
                                            "hover:min-h-fit",
                                            "hover:h-full",
                                            "overflow-hidden",
                                        )
                                        p {
                                            classes = setOf("font-semibold", "text-${event.colour}-700")
                                            +event.title
                                        }
                                        p {
                                            classes =
                                                setOf(
                                                    "text-${event.colour}-500",
                                                    "group-hover:text-${event.colour}-700"
                                                )
                                            time {
                                                +event.startTime.format(DateTimeFormatter.ofPattern("h:mm a"))
                                                    .uppercase()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun FlowContent.timetableWithBorder(timetable: Timetable) = div {
    classes = setOf("relative overflow-hidden py-16")
    div {
        classes = setOf("mx-auto max-w-7xl px-6 lg:px-8")
        div {
            classes = setOf("rounded-xl", "shadow-2xl", "ring-1", "ring-gray-900/10", "pb-4")
            timetable(timetable)
        }
    }
}