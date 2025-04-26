package app

data class EnvironmentData(
    val price: String,
    val baseUrl: String,
    val trialEnabled: Boolean,
    val stripeApiKey: String = System.getenv("STRIPE_API_KEY")
        ?: throw IllegalStateException("'STRIPE_API_KEY' must be present"),
)

private val stack = System.getenv("STACK")

val Environment = mapOf(
    "local" to EnvironmentData(
        price = "price_1RHnG2KOil4fztrBrIMwtmNY",
        baseUrl = "http://localhost:9090",
        trialEnabled = true,
    ),
    "production" to EnvironmentData(
        price = "",
        baseUrl = "",
        trialEnabled = false,
    ),
)[stack] ?: throw IllegalStateException("Invalid 'STACK' variable. Should be 'local' or 'prod', was '$stack'")


fun checkEnv() {
    assert(Environment.price.isNotBlank())
    assert(Environment.baseUrl.isNotBlank())
    assert(Environment.stripeApiKey.isNotBlank())
}