package ru.shvets.ktor.dao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import ru.shvets.common.model.People
import ru.shvets.common.model.Person
import ru.shvets.common.util.fromLocalDateToKotlinInstant
import ru.shvets.common.util.fromStringToLocalDate
import ru.shvets.ktor.config.DatabaseFactory.dbQuery

class DAOPersonImpl : DAOPerson {
    override suspend fun getAllPeople(): List<Person> {
        return dbQuery {
            People
                .selectAll()
                .map(::resultRowToPerson)
        }
    }

    override suspend fun getPerson(id: Long): Person? {
        return dbQuery {
            People
                .select { People.id eq id }
                .map(::resultRowToPerson)
                .singleOrNull()
        }
    }

    override suspend fun addPerson(
        firstName: String,
        lastName: String,
        dateOfBirth: String,
        phone: String,
    ): Person? = dbQuery {
        val insertStatement = People.insert {
            it[People.firstName] = firstName
            it[People.lastName] = lastName
            it[People.dateOfBirth] = dateOfBirth.fromStringToLocalDate()
            it[People.phone] = phone
            it[People.addressId] = null // TODO
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToPerson)
    }

    override suspend fun editPerson(
        id: Long,
        firstName: String,
        lastName: String,
        dateOfBirth: String,
        phone: String,
    ): Boolean = dbQuery {
        People.update({ People.id eq id }) {
            it[People.firstName] = firstName
            it[People.lastName] = lastName
            it[People.dateOfBirth] = dateOfBirth.fromStringToLocalDate()
            it[People.phone] = phone
            it[People.addressId] = null // TODO
        } > 0
    }

    override suspend fun deletePerson(id: Long): Boolean = dbQuery {
        People.deleteWhere { People.id eq id } > 0
    }

    private fun resultRowToPerson(
        row: ResultRow,
    ): Person {
        return Person(
            id = row[People.id],
            firstName = row[People.firstName],
            lastName = row[People.lastName],
            dateOfBirth = row[People.dateOfBirth]?.fromLocalDateToKotlinInstant(),
            phone = row[People.phone],
            addressId = row[People.addressId]
        )
    }
}

val daoPerson: DAOPerson = DAOPersonImpl()