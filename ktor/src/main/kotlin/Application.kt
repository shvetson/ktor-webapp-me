package ru.shvets.ktor

import ru.shvets.ktor.plugins.configureThymeleaf
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.shvets.ktor.plugins.configureDatabase
import ru.shvets.ktor.plugins.configureRouting

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {

    configureDatabase()
    configureRouting()
//    configureSerialization() // при работе с templating отключать
    configureThymeleaf()
}