package ru.shvets.ktor.plugins

import io.ktor.server.application.*
import ru.shvets.ktor.config.DatabaseFactory

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  10.04.2023 13:59
 */

fun Application.configureDatabase() {
    DatabaseFactory.init()
}