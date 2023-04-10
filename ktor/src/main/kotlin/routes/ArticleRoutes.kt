package ru.shvets.ktor.routes

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.*
import io.ktor.server.util.*
import ru.shvets.ktor.dao.dao
import ru.shvets.ktor.models.Article

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  26.03.2023 12:31
 */

fun Route.articleRouting() {

    route("articles") {

        get("") {
            val model = mapOf("articles" to dao.allArticles())
            call.respond(ThymeleafContent("/articles/index.html", model))
        }

        get("new") {
            val model = emptyMap<String, Any>()
            call.respond(ThymeleafContent("/articles/new.html", model))
        }

        post("new") {
            val formParameters = call.receiveParameters()
            val title = formParameters.getOrFail("title")
            val body = formParameters.getOrFail("body")
            val article = dao.addNewArticle(title, body)
            call.respondRedirect("/api/v1/articles")
//            call.respondRedirect("/api/v1/articles/${article?.id}")
        }

        get("{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: throw Exception("Invalid id")
//            val id = call.parameters.getOrFail<Int>("id").toInt()
            val article: Article = dao.article(id).takeIf { it != null } ?: throw Exception("Invalid id")
            val model = mapOf("article" to article)
            call.respond(ThymeleafContent("/articles/show.html", model))
        }

        get("{id}/edit") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val article: Article = dao.article(id).takeIf { it != null } ?: throw Exception("Invalid id")
            val model = mapOf("article" to article)
            call.respond(ThymeleafContent("/articles/edit.html", model))
        }

        post("{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val formParameters = call.receiveParameters()
            when (formParameters.getOrFail("_action")) {
                "update" -> {
                    val title = formParameters.getOrFail("title")
                    val body = formParameters.getOrFail("body")
                    dao.editArticle(id, title, body)
                    call.respondRedirect("/api/v1/articles")
//                    call.respondRedirect("/api/v1/articles/$id")
                }

                "delete" -> {
                    dao.deleteArticle(id)
                    call.respondRedirect("/api/v1/articles")
                }
            }
        }
    }
}
