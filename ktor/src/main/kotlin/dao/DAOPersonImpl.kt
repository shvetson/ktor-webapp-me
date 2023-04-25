package ru.shvets.ktor.dao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import ru.shvets.common.dto.ContactDto
import ru.shvets.common.dto.PersonDto
import ru.shvets.common.dto.PersonWithAddressDto
import ru.shvets.common.model.*
import ru.shvets.common.util.fromKotlinInstantToLocalDateString
import ru.shvets.common.util.fromLocalDateToKotlinInstant
import ru.shvets.common.util.fromStringToLocalDate
import ru.shvets.ktor.config.DatabaseFactory.dbQuery
import ru.shvets.ktor.mapper.toContactDto
import ru.shvets.ktor.mapper.toContactsType
import ru.shvets.ktor.mapper.toPersonDto

class DAOPersonImpl : DAOPerson {

    override suspend fun getAllPeopleAddress(): List<PersonWithAddressDto> { // не используется
        return dbQuery {
            (PersonTable leftJoin AddressTable)
                .selectAll()
                .map(::resultRowToPersonAddress)
        }
    }

    override suspend fun getAllPeople(): List<PersonDto> {
        return dbQuery {
            PersonTable
                .selectAll()
                .map(::resultRowToPersonDto)
        }
    }

//    override suspend fun getPerson(id: Long): Person? {
//        return dbQuery {
//
//            val contacts = ContactTable
//                .select(ContactTable.personId eq id)
//                .map {
//                    Contact(
//                        id = it[ContactTable.id].value,
//                        type = ContactType.valueOf(it[ContactTable.type]),
//                        value = it[ContactTable.value],
//                        personId = it[ContactTable.personId]
//                    )
//                }
//
//            PersonTable
//                .select { PersonTable.id eq id }
//                .singleOrNull()?.let {
//                    Person(
//                        id = it[PersonTable.id],
//                        firstName = it[PersonTable.firstName],
//                        lastName = it[PersonTable.lastName],
//                        dateOfBirth = it[PersonTable.dateOfBirth]?.fromLocalDateToKotlinInstant(),
//                        phone = it[PersonTable.phone],
//                        contacts = contacts
//                    )
//                }
//        }
//    }

    override suspend fun getPerson(id: Long): PersonDto? {
        return dbQuery {
            PersonTable
                .select { PersonTable.id eq id }
                .map { it.toPersonDto() }
                .singleOrNull()
        }
    }

    override suspend fun getPersonAddress(id: Long): PersonWithAddressDto? {
        return dbQuery {
            (PersonTable leftJoin AddressTable)
                .select { PersonTable.id eq id }
                .map(::resultRowToPersonAddress)
                .singleOrNull()
        }
    }

    override suspend fun addPerson(personDto: PersonDto): PersonDto? = dbQuery {
        val insertStatement = PersonTable.insert {
            it[PersonTable.firstName] = personDto.firstName
            it[PersonTable.lastName] = personDto.lastName
            it[PersonTable.dateOfBirth] = personDto.dateOfBirth?.fromStringToLocalDate()
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToPersonDto)
    }

    override suspend fun editPerson(id: Long, personDto: PersonDto): Boolean = dbQuery {
        PersonTable.update({ PersonTable.id eq id }) {
            it[PersonTable.firstName] = personDto.firstName
            it[PersonTable.lastName] = personDto.lastName
            it[PersonTable.dateOfBirth] = personDto.dateOfBirth?.fromStringToLocalDate()
        } > 0
    }

    override suspend fun deletePerson(id: Long): Boolean = dbQuery {
        PersonTable.deleteWhere { PersonTable.id eq id } > 0
    }

    private fun resultRowToPersonDto(row: ResultRow): PersonDto {
        return PersonDto(
            id = row[PersonTable.id],
            firstName = row[PersonTable.firstName],
            lastName = row[PersonTable.lastName],
            dateOfBirth = row[PersonTable.dateOfBirth]?.fromLocalDateToKotlinInstant()
                ?.fromKotlinInstantToLocalDateString() ?: "",
        )
    }

    private fun resultRowToPersonAddress(row: ResultRow): PersonWithAddressDto { // удалить после fix edit feature
        return PersonWithAddressDto(
            id = row[PersonTable.id],
            firstName = row[PersonTable.firstName],
            lastName = row[PersonTable.lastName],
            dateOfBirth = row[PersonTable.dateOfBirth]?.fromLocalDateToKotlinInstant()
                ?.fromKotlinInstantToLocalDateString() ?: "",
            postCode = row[AddressTable.postCode],
            region = row[AddressTable.region],
            city = row[AddressTable.city],
            street = row[AddressTable.street],
            house = row[AddressTable.house],
            building = row[AddressTable.building],
            flat = row[AddressTable.flat]
        )
    }
}

val daoPerson: DAOPerson = DAOPersonImpl()