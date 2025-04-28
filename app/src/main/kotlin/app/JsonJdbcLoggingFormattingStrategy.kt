package app

import com.p6spy.engine.spy.appender.MessageFormattingStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class JsonJdbcLoggingFormattingStrategy : MessageFormattingStrategy {
    private val json = Json {
        prettyPrint = true
    }
    override fun formatMessage(
        connectionId: Int,
        now: String?,
        elapsed: Long,
        category: String?,
        prepared: String?,
        sql: String?,
        url: String?,
    ): String {
        return json.encodeToString(
            JdbcLogMessage(
                connectionId,
                now,
                elapsed,
                category,
                prepared,
                sql,
                url,
            )
        )
    }
}

@Serializable
private data class JdbcLogMessage(
    val connectionId: Int,
    val now: String?,
    val elapsed: Long,
    val category: String?,
    val prepared: String?,
    val sql: String?,
    val url: String?,
)