package ru.shvets.ktor.dao

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import ru.shvets.common.model.*
import ru.shvets.common.util.fromKotlinInstantToLocalDateString
import ru.shvets.common.util.fromLocalDateToKotlinInstant
import ru.shvets.common.util.fromStringToLocalDate
import ru.shvets.ktor.config.DatabaseFactory.dbQuery

class DAOPersonImpl : DAOPerson {

    override suspend fun getAllPeopleAddress(): List<PersonWithAddress> {
        return dbQuery {
            (People leftJoin Addresses)
                .selectAll()
                .map(::resultRowToPersonAddress)
        }
    }

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

    override suspend fun getPersonAddress(id: Long): PersonWithAddress? {
        return dbQuery {
            (People leftJoin Addresses)
                .select { People.id eq id }
                .map(::resultRowToPersonAddress)
                .singleOrNull()
        }
    }

    override suspend fun addPerson(person: Person, addressId: Long): Person? = dbQuery {
        val insertStatement = People.insert {
            it[People.firstName] = person.firstName
            it[People.lastName] = person.lastName
            it[People.dateOfBirth] = person.dateOfBirth?.toLocalDateTime(TimeZone.currentSystemDefault())?.date?.toJavaLocalDate()
            it[People.phone] = person.phone
            it[People.addressId] = addressId
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToPerson)
    }

    override suspend fun editPerson(id: Long, person: Person, addressId: Long): Boolean = dbQuery {
        People.update({ People.id eq id }) {
            it[People.firstName] = person.firstName
            it[People.lastName] = person.lastName
            it[People.dateOfBirth] = person.dateOfBirth?.toLocalDateTime(TimeZone.currentSystemDefault())?.date?.toJavaLocalDate()
            it[People.phone] = person.phone
            it[People.addressId] = addressId
        } > 0
    }

    override suspend fun deletePerson(id: Long): Boolean = dbQuery {
        People.deleteWhere { People.id eq id } > 0
    }

    private fun resultRowToPerson(row: ResultRow): Person {
        return Person(
            id = row[People.id],
            firstName = row[People.firstName],
            lastName = row[People.lastName],
            dateOfBirth = row[People.dateOfBirth]?.fromLocalDateToKotlinInstant(),
            phone = row[People.phone],
            addressId = row[People.addressId]
        )
    }

    private fun resultRowToPersonAddress(row: ResultRow): PersonWithAddress {
        return PersonWithAddress(
            id = row[People.id],
            firstName = row[People.firstName],
            lastName = row[People.lastName],
            dateOfBirth = row[People.dateOfBirth]?.fromLocalDateToKotlinInstant()?.fromKotlinInstantToLocalDateString() ?: "",
            phone = row[People.phone] ?: "",
            postCode = row[Addresses.postCode],
            region = row[Addresses.region],
            city = row[Addresses.city],
            street = row[Addresses.street],
            house = row[Addresses.house],
            building = row[Addresses.building],
            flat = row[Addresses.flat]
        )
    }
}

val daoPerson: DAOPerson = DAOPersonImpl()