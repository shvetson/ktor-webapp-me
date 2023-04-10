package ru.shvets.ktor.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  24.03.2023 09:00
 */

fun Application.configureSerialization() {
    install(ContentNegotiation){
        json()
    }
}