package ru.shvets.ktor.routes

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.*
import io.ktor.server.util.*
import ru.shvets.ktor.config.Log.logger
import ru.shvets.ktor.dao.daoAddress
import ru.shvets.ktor.dao.daoContact
import ru.shvets.ktor.dao.daoPerson
import ru.shvets.ktor.utils.createAddress
import ru.shvets.ktor.utils.createContact
import ru.shvets.ktor.utils.createPerson

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  26.03.2023 12:31
 */

fun Route.contactsRouting() {
    route("contacts") {

//        get("") {
////            val model = emptyMap<String, Any>()
//            val contactsTypes = daoPerson.getAllContactsTypes()
//            val model = mapOf("contactsTypes" to contactsTypes)
//            call.respond(ThymeleafContent("/people/new.html", model))
//        }
//
        post() {
            val formParameters = call.receiveParameters()
            val personId = formParameters.getOrFail("personId").toLong()
            val typeId = formParameters.getOrFail("typeId").toLong()
            val data = formParameters.getOrFail("data")
            daoContact.addContact(personId, typeId, data)
            call.respondRedirect("/api/v1/people/$personId/edit")
        }
//
//        get("{id}") {
//            // val id = call.parameters.getOrFail<Int>("id").toInt()
//            val id = call.parameters["id"]?.toLongOrNull() ?: throw Exception("Invalid person id")
//            val person = daoPerson.getPerson(id).takeIf { it != null } ?: throw Exception("Invalid person id")
//            val model = mapOf("person" to person)
//            call.respond(ThymeleafContent("/people/show.html", model))
//        }

        // удалить
        get("{id}/edit") {
            val id = call.parameters.getOrFail<Long>("id").toLong()
            val contact = daoContact.getContact(id).takeIf { it != null } ?: throw Exception("Invalid contact id")
            val contactsTypes = daoContact.getAllContactsTypes()
            val model = mapOf("contact" to contact, "contactsTypes" to contactsTypes)
            call.respond(ThymeleafContent("/contact/contactEdit.html", model))
        }

        post("{id}") {
            val id = call.parameters.getOrFail<Long>("id").toLong()
            val formParameters = call.receiveParameters()
            val personId = daoContact.getPersonIdByContactId(id)
            val typeId = formParameters.getOrFail("typeId").toLong()
            val data = formParameters.getOrFail("data")
            daoContact.editContact(id, typeId, data)
            call.respondRedirect("/api/v1/people/$personId/edit")
        }

        get("{id}/delete") {
            val id = call.parameters["id"]?.toLongOrNull() ?: throw Exception("Invalid contact id")
            val personId = daoContact.getPersonIdByContactId(id)
            daoContact.deleteContact(id)
            call.respondRedirect("/api/v1/people/$personId/edit")
        }
    }
}