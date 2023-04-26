package ru.shvets.ktor.routes

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import ru.shvets.ktor.dao.daoContact

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  26.03.2023 12:31
 */

fun Route.contactsRouting() {
    route("contacts") {

        // добавление нового контакта (сохранение в базе данных)
        // и возврат на страницу редактирования информации
        post() {
            val formParameters = call.receiveParameters()
            val personId = formParameters.getOrFail("personId").toLong()
            val typeId = formParameters.getOrFail("typeId").toLong()
            val data = formParameters.getOrFail("data")
            daoContact.addContact(personId, typeId, data)
            call.respondRedirect("/api/v1/people/$personId/edit")
        }

        // корректировка контактной информации (сохранение изменений в базе данных)
        // и возврат на страницу редактирования информации
        post("{id}") {
            val id = call.parameters.getOrFail<Long>("id").toLong()
            val formParameters = call.receiveParameters()
            val personId = daoContact.getPersonIdByContactId(id)
            val typeId = formParameters.getOrFail("typeId").toLong()
            val data = formParameters.getOrFail("data")
            daoContact.editContact(id, typeId, data)
            call.respondRedirect("/api/v1/people/$personId/edit")
        }

        // удаление записи по id контакта и возврат на страницу редактирования информации
        get("{id}/delete") {
            val id = call.parameters["id"]?.toLongOrNull() ?: throw Exception("Invalid contact id")
            val personId = daoContact.getPersonIdByContactId(id)
            daoContact.deleteContact(id)
            call.respondRedirect("/api/v1/people/$personId/edit")
        }
    }
}