package repository

import app.exampleEvents
import java.time.Instant
import java.util.UUID
import view.Event

interface TimeTableRepository {
    fun save(timetable: Timetable)
    fun update(privateId: UUID, timetable: Timetable)
    fun loadByPrivateId(id: UUID): Timetable?
    fun loadByPublicId(id: UUID): Timetable?

    object InMemory : TimeTableRepository {
        private val timetables = mutableListOf<Timetable>()
        override fun save(timetable: Timetable) {
            timetables.add(timetable)
        }

        override fun update(privateId: UUID, timetable: Timetable) {
            timetables.replaceAll { if (it.privateId == privateId) timetable else it }
        }

        override fun loadByPublicId(id: UUID): Timetable? {
            return timetables.find { it.publicId == id }
        }

        override fun loadByPrivateId(id: UUID): Timetable? {
            return timetables.find { it.privateId == id }
        }
    }
}

data class Timetable(
    val privateId: UUID,
    val publicId: UUID,
    val events: List<Event>,
    val createdAt: Instant = Instant.now(),
    val title: String? = null,
)

val exampleTimetable = Timetable(
    privateId = UUID.randomUUID(),
    publicId = UUID.randomUUID(),
    events = exampleEvents,
)
