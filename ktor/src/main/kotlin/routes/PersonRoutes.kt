package ru.shvets.ktor.routes

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.*
import io.ktor.server.util.*
import ru.shvets.common.dto.toDto
import ru.shvets.common.model.Person
import ru.shvets.ktor.dao.daoAddress
import ru.shvets.ktor.dao.daoPerson
import ru.shvets.ktor.utils.createAddress
import ru.shvets.ktor.utils.createPerson

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  26.03.2023 12:31
 */

fun Route.peopleRouting() {
    route("people") {

        get("") {
            val people = daoPerson.getAllPeopleAddress()
            val model = mapOf("people" to people)
            call.respond(ThymeleafContent("/people/index.html", model))
        }

//        get("") {
//            val people = daoPerson.getAllPeople().map { it.toDto() }
//            val model = mapOf("people" to people)
//            call.respond(ThymeleafContent("/people/index.html", model))
//        }

        get("new") {
            val model = emptyMap<String, Any>()
            call.respond(ThymeleafContent("/people/new.html", model))
        }

        post("new") {
            val formParameters = call.receiveParameters()
            val personFromParam = createPerson(formParameters)
            val addressFromParam = createAddress(formParameters)
            val address = daoAddress.addAddress(addressFromParam)
            val person = address?.let { it -> daoPerson.addPerson(personFromParam, it.id) }
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
            val model = mutableMapOf<String, Any>()
            val id = call.parameters.getOrFail<Long>("id").toLong()
            val person = daoPerson.getPersonAddress(id).takeIf { it != null } ?: throw Exception("Invalid id")
            model["person"] = person
            call.respond(ThymeleafContent("/people/edit.html", model))
        }

        post("{id}") {
            val id = call.parameters.getOrFail<Long>("id").toLong()
            val formParameters = call.receiveParameters()

            when (formParameters.getOrFail("_action")) {
                "save" -> {
                    val firstName = formParameters.getOrFail("firstName")
                    val lastName = formParameters.getOrFail("lastName")
                    val dateOfBirth = formParameters.getOrFail("dateOfBirth")
                    val phone = formParameters.getOrFail("phone")

                    val addressId = daoPerson.getPerson(id)?.addressId ?: 0L
                    val postCode = formParameters.getOrFail("postCode").toIntOrNull() ?: 0
                    val region = formParameters.getOrFail("region")
                    val city = formParameters.getOrFail("city")
                    val street = formParameters.getOrFail("street")
                    val house = formParameters.getOrFail("house")
                    val building = formParameters.getOrFail("building")
                    val flat = formParameters.getOrFail("flat")

                    if (addressId == 0L) {
                        val address = daoAddress.addAddress(postCode, region, city, street, house, building, flat)
                        address?.let { it -> daoPerson.addPerson(firstName, lastName, dateOfBirth, phone, it.id) }
                    } else {
                        daoAddress.editAddress(addressId, postCode, region, city, street, house, building, flat)
                    }

                    daoPerson.editPerson(id, firstName, lastName, dateOfBirth, phone, addressId)
                    call.respondRedirect("/api/v1/people")
//                    call.respondRedirect("/api/v1/articles/$id")
                }

                "delete" -> {
                    daoPerson.deletePerson(id)
                    call.respondRedirect("/api/v1/people")
                }
            }
        }

        get("{id}/delete") {
            val id = call.parameters["id"]?.toLongOrNull() ?: throw Exception("Invalid id")
            val person = daoPerson.getPerson(id)

            if (daoAddress.deleteAddress(person?.addressId ?: 0L)) {
                daoPerson.deletePerson(id)
            }
            call.respondRedirect("/api/v1/people")
        }
    }
}