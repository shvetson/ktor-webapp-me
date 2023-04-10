package ru.shvets.ktor.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.yaml.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import ru.shvets.kafka.model.entity.Cities
import ru.shvets.kafka.model.entity.City
import ru.shvets.kafka.model.entity.People
import ru.shvets.kafka.model.entity.Person
import ru.shvets.ktor.config.Log.logger

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  26.03.2023 10:33
 */

object DatabaseFactory {
    private val yamlConfig = YamlConfig(path = "application.yaml")

    private fun createHikariDataSource(
        url: String,
        driver: String,
        user: String,
        pass: String,
    ) = HikariDataSource(HikariConfig().apply {
        driverClassName = driver
        jdbcUrl = url
        username = user
        password = pass
        maximumPoolSize = 3
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    })

    fun init() {
        var database: Database? = null
        val driver = yamlConfig?.propertyOrNull("ktor.datasource.driver")?.getString() ?: ""
        val url = yamlConfig?.propertyOrNull("ktor.datasource.url")?.getString() ?: ""
        val username = yamlConfig?.propertyOrNull("ktor.datasource.username")?.getString() ?: ""
        val password = yamlConfig?.propertyOrNull("ktor.datasource.password")?.getString() ?: ""

        if ((driver != "") && (url != "") && (username != "") && (password != "")) {
            val datasource = createHikariDataSource(url, driver, username, password)
            database = Database.connect(datasource)
            logger.info("Database init")
        }

        transaction(database) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create (Cities, People)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}