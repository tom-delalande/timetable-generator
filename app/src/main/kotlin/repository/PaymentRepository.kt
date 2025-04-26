package repository

import java.util.UUID

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
}
