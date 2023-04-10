package ru.shvets.ktor.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.*
import ru.shvets.ktor.models.User

fun Route.userRouting() {
        get("/index") {
            val sampleUser = User(1, "Oleg Shvets")
            println(sampleUser)
            call.respond(ThymeleafContent("index.html", mapOf("user" to sampleUser)))
        }
}