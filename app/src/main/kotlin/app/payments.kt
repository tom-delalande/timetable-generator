package app

import com.stripe.StripeClient
import com.stripe.param.checkout.SessionCreateParams
import com.stripe.param.checkout.SessionCreateParams.LineItem
import java.util.UUID

@JvmInline
value class SessionUrl(val url: String)

object Payments {
    fun createSession(paymentId: UUID, timetableId: UUID): SessionUrl {
        val stripe = StripeClient(Environment.stripeApiKey)
        val session = stripe.checkout().sessions().create(
            SessionCreateParams.builder()
                .addLineItem(
                    LineItem.builder()
                        .setPrice(Environment.price)
                        .setQuantity(1)
                        .build()
                )
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSuccessUrl("${Environment.baseUrl}/payments/success/payment/$paymentId")
                .setCancelUrl("${Environment.baseUrl}/payments/cancel/timetable/$timetableId")
                .build()
        )
        return SessionUrl(session.url)
    }
}