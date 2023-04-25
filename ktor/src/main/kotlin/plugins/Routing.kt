package ru.shvets.ktor.plugins

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.util.*
import ru.shvets.ktor.routes.*

fun Application.configureRouting() {

    routing {
        static("/static") {
            resources("files")
        }

        route("/api/v1") {
            peopleRouting()
            contactsRouting()

        }
    }

    routing {
        userRouting()
    }
}
