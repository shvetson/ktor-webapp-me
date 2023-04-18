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

        get("new") {
            val model = emptyMap<String, Any>()
            call.respond(ThymeleafContent("/people/new.html", model))
        }

        post("new") {
            val formParameters = call.receiveParameters()
            val personFromParam = createPerson(formParameters)
            val addressFromParam = createAddress(formParameters)

            val address = daoAddress.addAddress(addressFromParam)
            address?.let { daoPerson.addPerson(personFromParam, it.id) }
            call.respondRedirect("/api/v1/people")
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
            var addressId = daoPerson.getPerson(id)?.addressId ?: 0L

            val formParameters = call.receiveParameters()
            val personFromParam = createPerson(formParameters)
            val addressFromParam = createAddress(formParameters)

            //добавить - проверка на ввод данных по адресу
            if (addressId == 0L) { addressId = daoAddress.addAddress(addressFromParam)?.id ?: 0L }
            else { daoAddress.editAddress(addressId, addressFromParam) }

            daoPerson.editPerson(id, personFromParam, addressId)
            call.respondRedirect("/api/v1/people")
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