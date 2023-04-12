package ru.shvets.ktor.routes

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.*
import io.ktor.server.util.*
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import ru.shvets.common.NONE
import ru.shvets.common.dto.toDto
import ru.shvets.common.model.Person
import ru.shvets.ktor.config.Log.logger
import ru.shvets.ktor.dao.daoPerson
import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  26.03.2023 12:31
 */

fun Route.peopleRouting() {

    route("people") {

        get("") {
            val people = daoPerson.getAllPeople()
            val model = mapOf("people" to people)
            call.respond(ThymeleafContent("/people/index.html", model))
        }

        get("new") {
            val model = emptyMap<String, Any>()
            call.respond(ThymeleafContent("/people/new.html", model))
        }

        post("new") {
            val formParameters = call.receiveParameters()
            val firstName = formParameters.getOrFail("firstName")
            val lastname = formParameters.getOrFail("lastName")
            val dateOfBirth = formParameters.getOrFail("dateOfBirth")
            val phone = formParameters.getOrFail("phone")
            val person = daoPerson.addPerson(firstName, lastname, dateOfBirth, phone)
            call.respondRedirect("/api/v1/people")
//            call.respondRedirect("/api/v1/people/${person?.id}")
        }

        get("{id}") {
            val id = call.parameters["id"]?.toLongOrNull() ?: throw Exception("Invalid id")
//            val id = call.parameters.getOrFail<Int>("id").toInt()
            val person: Person = daoPerson.getPerson(id).takeIf { it != null } ?: throw Exception("Invalid id")
            val model = mapOf("person" to person.toDto())
            call.respond(ThymeleafContent("/people/show.html", model))
        }

        get("{id}/edit") {
            val id = call.parameters.getOrFail<Long>("id").toLong()
            val person: Person = daoPerson.getPerson(id).takeIf { it != null } ?: throw Exception("Invalid id")
            val model = mapOf("person" to person.toDto())
            call.respond(ThymeleafContent("/people/edit.html", model))
        }

        post("{id}") {
            val id = call.parameters.getOrFail<Long>("id").toLong()
            val formParameters = call.receiveParameters()
            when (formParameters.getOrFail("_action")) {
                "update" -> {
                    val firstName = formParameters.getOrFail("firstName")
                    val lastname = formParameters.getOrFail("lastName")
                    val dateOfBirth = formParameters.getOrFail("dateOfBirth")
                    val phone = formParameters.getOrFail("phone")

                    daoPerson.editPerson(id, firstName, lastname, dateOfBirth, phone)
                    call.respondRedirect("/api/v1/people")
//                    call.respondRedirect("/api/v1/articles/$id")
                }
                "delete" -> {
                    daoPerson.deletePerson(id)
                    call.respondRedirect("/api/v1/people")
                }
            }
        }
    }
}
