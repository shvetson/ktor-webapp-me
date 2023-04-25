package ru.shvets.ktor.dao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import ru.shvets.common.dto.ContactDto
import ru.shvets.common.dto.PersonDto
import ru.shvets.common.model.*
import ru.shvets.common.util.fromStringToLocalDate
import ru.shvets.ktor.config.DatabaseFactory.dbQuery
import ru.shvets.ktor.mapper.toContactDto
import ru.shvets.ktor.mapper.toContactsType

class DAOContactImpl : DAOContact {

    override suspend fun getContact(id: Long): ContactDto? = dbQuery {
        (ContactTable leftJoin ContactTypeTable)
            .select(ContactTable.id eq id)
            .map { it.toContactDto() }
            .singleOrNull()
    }

    override suspend fun getPersonIdByContactId(id: Long): Long? = dbQuery {
        ContactTable
            .slice(ContactTable.personId)
            .select(ContactTable.id eq id)
            .map { it[ContactTable.personId] }
            .singleOrNull()
    }

    override suspend fun getAllContacts(personId: Long): List<ContactDto>? = dbQuery {
        (ContactTable leftJoin ContactTypeTable)
            .select(ContactTable.personId eq personId)
            .map { it.toContactDto() }
    }

    override suspend fun getAllContactsTypes(): List<ContactType> = dbQuery {
        ContactTypeTable
            .selectAll()
            .map { it.toContactsType() }
    }

    override suspend fun addContact(personId: Long, typeId: Long, data: String): Contact? = dbQuery {
        val insertStatement = ContactTable.insert {
            it[ContactTable.typeId] = typeId
            it[ContactTable.data] = data
            it[ContactTable.personId] = personId
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToContact)
    }

    override suspend fun editContact(id: Long, typeId: Long, data: String): Boolean = dbQuery {
        ContactTable.update({ ContactTable.id eq id }) {
            it[ContactTable.typeId] = typeId
            it[ContactTable.data] = data
        } > 0
    }

    override suspend fun deleteContact(id: Long): Boolean = dbQuery {
        ContactTable.deleteWhere { ContactTable.id eq id } > 0
    }

    // возврат нового объекта (записи), добавленного в таблицу contacts
    private fun resultRowToContact(row: ResultRow): Contact {
        return Contact(
            id = row[ContactTable.id].value,
            typeId = row[ContactTable.typeId],
            data = row[ContactTable.data],
            personId = row[ContactTable.personId]
        )
    }
}

val daoContact: DAOContact = DAOContactImpl()