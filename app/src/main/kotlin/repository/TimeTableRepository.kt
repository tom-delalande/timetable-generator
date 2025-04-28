package repository

import app.exampleEvents
import java.sql.Timestamp
import java.sql.Types
import java.time.Instant
import java.util.UUID
import javax.sql.DataSource
import kotlinx.serialization.json.Json
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

    class SQL(private val datasource: DataSource) : TimeTableRepository {
        private val json = Json

        override fun save(timetable: Timetable) {
            datasource.connection.use { connection ->
                connection.prepareStatement(
                    """
                    INSERT INTO timetables (public_id, private_id, events, created_at)
                    VALUES (?, ?, ?, ?)
                """.trimIndent()
                ).use { statement ->
                    statement.setObject(1, timetable.publicId.toString(), Types.OTHER)
                    statement.setObject(2, timetable.privateId.toString(), Types.OTHER)
                    statement.setObject(3, json.encodeToString(timetable.events), Types.OTHER)
                    statement.setTimestamp(4, Timestamp.from(timetable.createdAt))
                    statement.executeUpdate()
                }
            }
        }

        override fun update(privateId: UUID, timetable: Timetable) {
            datasource.connection.use { connection ->
                connection.prepareStatement(
                    """
                    UPDATE timetables
                    SET events = ?
                    WHERE private_id = ?
                """.trimIndent()
                ).use { statement ->
                    statement.setObject(1, json.encodeToString(timetable.events), Types.OTHER)
                    statement.setObject(2, timetable.privateId.toString(), Types.OTHER)
                    statement.executeUpdate()
                }
            }
        }

        override fun loadByPrivateId(id: UUID): Timetable? {
            datasource.connection.use { connection ->
                connection.prepareStatement(
                    """
                    SELECT public_id, private_id, events, created_at
                    FROM timetables
                    WHERE private_id = ?
                """.trimIndent()
                ).use { statement ->
                    statement.setObject(1, id.toString(), Types.OTHER)
                    statement.executeQuery().use { set ->
                        return if (set.next()) {
                            Timetable(
                                publicId = set.getString("public_id").let { UUID.fromString(it) },
                                privateId = set.getString("private_id").let { UUID.fromString(it) },
                                events = set.getString("events").let { json.decodeFromString(it) },
                                createdAt = set.getTimestamp("created_at").toInstant(),
                            )
                        } else {
                            null
                        }
                    }
                }
            }
        }

        override fun loadByPublicId(id: UUID): Timetable? {
            datasource.connection.use { connection ->
                connection.prepareStatement(
                    """
                    SELECT public_id, private_id, events, created_at
                    FROM timetables
                    WHERE public_id = ?
                """.trimIndent()
                ).use { statement ->
                    statement.setObject(1, id.toString(), Types.OTHER)
                    statement.executeQuery().use { set ->
                        return if (set.next()) {
                            Timetable(
                                publicId = set.getString("public_id").let { UUID.fromString(it) },
                                privateId = set.getString("private_id").let { UUID.fromString(it) },
                                events = set.getString("events").let { json.decodeFromString(it) },
                                createdAt = set.getTimestamp("created_at").toInstant(),
                            )
                        } else {
                            null
                        }
                    }
                }
            }
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
