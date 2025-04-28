package repository

import javax.sql.DataSource
import org.flywaydb.core.Flyway

fun migrate(dataSource: DataSource) {
   Flyway.configure()
        .locations("filesystem:./db/migrations")
        .dataSource(dataSource)
        .load()
        .migrate()
}

