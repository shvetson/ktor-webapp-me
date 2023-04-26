package ru.shvets.ktor.routes

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.*
import io.ktor.server.util.*
import ru.shvets.ktor.dao.daoAddress
import ru.shvets.ktor.dao.daoContact
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

        get("index") {
            val people = daoPerson.getAllPeople()
            val model = mapOf("people" to people)
            call.respond(ThymeleafContent("/people/index.html", model))
        }

        // добавление новой записи (вызов формы ввода - общая, адрес и контакт)
        get() {
//            val model = emptyMap<String, Any>()
            val contactsTypes = daoContact.getAllContactsTypes()
            val model = mapOf("contactsTypes" to contactsTypes)
            call.respond(ThymeleafContent("/people/new.html", model))
        }

        // добавление новой записи (сохранение данных в базе данных)
        post() {
            val formParameters = call.receiveParameters()

            val personParam = createPerson(formParameters)
            val addressParam = createAddress(formParameters)

            val contactTypeId = formParameters.getOrFail("typeId").toLong()
            val contactData = formParameters.getOrFail("data")

            val person = daoPerson.addPerson(personParam)
            person?.let { it -> daoAddress.addAddress(it.id ?: throw Exception("Invalid person id"), addressParam) }
            person?.let { it -> daoContact.addContact(it.id ?: throw Exception("Invalid person id"), contactTypeId, contactData) }
            call.respondRedirect("/api/v1/people/index")
        }

        // запрос данных по id для просмотра (вызов формы для отображения данных о персоне - общая, адрес и контакт)
        get("{id}") {
            // val id = call.parameters.getOrFail<Int>("id").toInt()
            val id = call.parameters["id"]?.toLongOrNull() ?: throw Exception("Invalid person id")
            val person = daoPerson.getPerson(id).takeIf { it != null } ?: throw Exception("Invalid person id")
            val model = mapOf("person" to person)
            call.respond(ThymeleafContent("/people/show.html", model))
        }

        // запрос данных по id для редактирования (вызов формы для редактирования данных о персоне - общая, адрес и контакт)
        get("{id}/edit") {
            val id = call.parameters.getOrFail<Long>("id").toLong()
            val person = daoPerson.getPersonAddress(id).takeIf { it != null } ?: throw Exception("Invalid person id")
            val address = daoAddress.getAddress(id).takeIf { it != null } ?: throw Exception("Invalid person id")
            val contacts = daoContact.getAllContacts(id).takeIf { it != null } ?: throw Exception("Invalid person id")
            val contactsTypes = daoContact.getAllContactsTypes()

            val model = mapOf("person" to person, "address" to address, "contacts" to contacts, "contactsTypes" to contactsTypes)
            call.respond(ThymeleafContent("/people/edit.html", model))
        }

        // корректировка общей информации (сохранение изменений в базе данных)
        post("{id}") {
            val id = call.parameters.getOrFail<Long>("id").toLong()
            val formParameters = call.receiveParameters()
            val personFromParam = createPerson(formParameters)
            daoPerson.editPerson(id, personFromParam)
//            call.respondRedirect("/api/v1/people")
            call.respondRedirect("/api/v1/people/$id/edit")
        }

        // корректировка информации об адресе (сохранение изменений в базе данных)
        post("{id}/address/edit"){
            val id = call.parameters.getOrFail<Long>("id").toLong()
            val formParameters = call.receiveParameters()
            val addressParam = createAddress(formParameters)
            daoAddress.editAddress(addressParam)
            call.respondRedirect("/api/v1/people/$id/edit")
        }

        // запрос данных для редактирования информации об контакте (проброска person id для возможности вернуться на
        // страницу редактирования данных и вызов формы ввода)
        get("{personId}/contact/{contactId}/edit"){
            val personId = call.parameters["personId"]?.toLongOrNull() ?: throw Exception("Invalid person id")
            val contactId = call.parameters["contactId"]?.toLongOrNull() ?: throw Exception("Invalid contact id")
            val contact = daoContact.getContact(contactId).takeIf { it != null } ?: throw Exception("Invalid contact id")
            val contactsTypes = daoContact.getAllContactsTypes()
            val model = mapOf("personId" to personId,"contact" to contact, "contactsTypes" to contactsTypes)
            call.respond(ThymeleafContent("/contact/contactEdit.html", model))
        }

        // добавление нового контакта (вызов формы ввода)
        get("{id}/contact") {
            val id = call.parameters["id"]?.toLongOrNull() ?: throw Exception("Invalid person id")
            val contactsTypes = daoContact.getAllContactsTypes()
            val model = mapOf("personId" to id, "contactsTypes" to contactsTypes)
            call.respond(ThymeleafContent("/contact/contactAdd.html", model))
        }

        // удаление записи по id персоны и возврат на страницу с общим списком
        get("{id}/delete") {
            val id = call.parameters["id"]?.toLongOrNull() ?: throw Exception("Invalid person id")
            daoPerson.deletePerson(id)
            call.respondRedirect("/api/v1/people/index")
        }
    }
}