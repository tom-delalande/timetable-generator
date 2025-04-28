package repository

import java.sql.Types
import java.util.UUID
import javax.sql.DataSource

interface PaymentRepository {
    fun start(timetableId: UUID, paymentId: UUID)
    fun success(paymentId: UUID)
    fun cancel(timetableId: UUID)
    fun check(timetableId: UUID): Boolean
    fun get(paymentId: UUID): UUID?

    object InMemory : PaymentRepository {
        data class Payment(
            val timetableId: UUID,
            val paymentId: UUID,
            val success: Boolean = false,
        )

        private val payments = mutableListOf<Payment>()

        override fun start(timetableId: UUID, paymentId: UUID) {
            payments.add(Payment(timetableId, paymentId))
        }

        override fun success(paymentId: UUID) {
            payments.replaceAll { if (it.paymentId == paymentId) it.copy(success = true) else it }
        }

        override fun cancel(timetableId: UUID) {
            payments.removeIf { it.timetableId == timetableId }
        }

        override fun check(timetableId: UUID): Boolean {
            return payments.find { it.timetableId == timetableId }?.success ?: false
        }

        override fun get(paymentId: UUID): UUID? {
            return payments.find { it.paymentId == paymentId }?.timetableId
        }
    }

    data class SQL(val datasource: DataSource) : PaymentRepository {
        override fun start(timetableId: UUID, paymentId: UUID) {
            datasource.connection.use { connection ->
                connection.prepareStatement(
                    """
                    INSERT INTO payments (timetable_private_id, payment_id, active)
                    VALUES (?, ?, ?)
                """.trimIndent()
                ).use { statement ->
                    statement.setObject(1, timetableId, Types.OTHER)
                    statement.setObject(2, paymentId, Types.OTHER)
                    statement.setObject(3, false, Types.OTHER)
                    statement.executeUpdate()
                }
            }
        }

        override fun success(paymentId: UUID) {
            datasource.connection.use { connection ->
                connection.prepareStatement(
                    """
                    UPDATE payments
                    SET active = true
                    WHERE payment_id = ?
                """.trimIndent()
                ).use { statement ->
                    statement.setObject(1, paymentId, Types.OTHER)
                    statement.executeUpdate()
                }
            }
        }

        override fun cancel(timetableId: UUID) {
            datasource.connection.use { connection ->
                connection.prepareStatement(
                    """
                    DELETE FROM payments
                    WHERE timetable_private_id = ?
                """.trimIndent()
                ).use { statement ->
                    statement.setObject(1, timetableId, Types.OTHER)
                    statement.executeUpdate()
                }
            }
        }

        override fun check(timetableId: UUID): Boolean {
            datasource.connection.use { connection ->
                connection.prepareStatement(
                    """
                    SELECT active FROM payments
                    WHERE timetable_private_id = ?
                    AND active = true
                """.trimIndent()
                ).use { statement ->
                    statement.setObject(1, timetableId, Types.OTHER)
                    statement.executeQuery().use { set ->
                        return if (set.next()) {
                            set.getBoolean("active")
                        } else {
                            false
                        }
                    }
                }
            }
        }

        override fun get(paymentId: UUID): UUID? {
            datasource.connection.use { connection ->
                connection.prepareStatement(
                    """
                    SELECT timetable_private_id FROM payments
                    WHERE payment_id = ?
                """.trimIndent()
                ).use { statement ->
                    statement.setObject(1, paymentId, Types.OTHER)
                    statement.executeQuery().use { set ->
                        return if (set.next()) {
                            set.getString("timetable_private_id")?.let { UUID.fromString(it) }
                        } else {
                            null
                        }
                    }
                }
            }
        }

    }
}
