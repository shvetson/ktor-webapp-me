package ru.shvets.ktor.dao

import ru.shvets.common.dto.ContactDto
import ru.shvets.common.model.Contact
import ru.shvets.common.model.ContactType

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  11.04.2023 20:21
 */

interface DAOContact {
    suspend fun getContact(id: Long): ContactDto?
    suspend fun getPersonIdByContactId(id: Long): Long?
    suspend fun getAllContacts(personId: Long): List<ContactDto>?
    suspend fun getAllContactsTypes(): List<ContactType>
    suspend fun addContact(personId: Long, typeId: Long, data: String): Contact?
    suspend fun editContact(id: Long, typeId: Long, data: String): Boolean
    suspend fun deleteContact(id: Long): Boolean
}