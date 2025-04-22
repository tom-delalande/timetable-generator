package repository

import view.Event

interface Repository {
    fun save(id: String, timetable: Timetable)
    fun load(id: String): Timetable?
}

data class Timetable(
    val events: List<Event>,
)

object InMemory : Repository {
    private val timetables = mutableMapOf<String, Timetable>()
    override fun save(id: String, timetable: Timetable) {
        timetables[id] = timetable
    }

    override fun load(id: String): Timetable? {
        return timetables[id]
    }

}