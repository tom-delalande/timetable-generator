package repository

import java.time.Instant
import java.util.UUID
import view.Event

interface TimeTableRepository {
    fun save(id: UUID, timetable: Timetable)
    fun load(id: UUID): Timetable?

    object InMemory : TimeTableRepository {
        private val timetables = mutableMapOf<UUID, Timetable>()
        override fun save(id: UUID, timetable: Timetable) {
            timetables[id] = timetable
        }

        override fun load(id: UUID): Timetable? {
            return timetables[id]
        }
    }
}

data class Timetable(
    val events: List<Event>,
    val createdAt: Instant = Instant.now(),
    val title: String? = null,
)
