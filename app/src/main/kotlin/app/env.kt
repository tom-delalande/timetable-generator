package app

import com.p6spy.engine.spy.P6DataSource
import javax.sql.DataSource
import org.sqlite.SQLiteDataSource

data class EnvironmentData(
    val price: String,
    val baseUrl: String,
    val trialEnabled: Boolean,
    val stripeApiKey: String = System.getenv("STRIPE_API_KEY")
        ?: throw IllegalStateException("'STRIPE_API_KEY' must be present"),
    val dataSource: DataSource,
)

private val stack = System.getenv("STACK")

val Environment = mapOf(
    "local" to EnvironmentData(
        price = "price_1RHnG2KOil4fztrBrIMwtmNY",
        baseUrl = "http://localhost:9090",
        trialEnabled = true,
        dataSource = SQLiteDataSource().apply {
            url = "jdbc:sqlite:./db/sqlite/data.sqlite"
        }.let { P6DataSource(it) }
    ),
    "production" to EnvironmentData(
        price = "price_1RHnESKOil4fztrBLzXRoQ8O",
        baseUrl = "https://timetable.tinyclub.io",
        trialEnabled = true,
        dataSource = SQLiteDataSource().apply {
            url = "jdbc:sqlite:./db/sqlite/data.sqlite"
        }.let { P6DataSource(it) }
    ),
)[stack] ?: throw IllegalStateException("Invalid 'STACK' variable. Should be 'local' or 'production', was '$stack'")


fun checkEnv() {
    assert(Environment.price.isNotBlank())
    assert(Environment.baseUrl.isNotBlank())
    assert(Environment.stripeApiKey.isNotBlank())
}