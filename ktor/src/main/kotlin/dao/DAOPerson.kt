package ru.shvets.ktor.dao

import ru.shvets.common.dto.PersonDto
import ru.shvets.common.dto.PersonWithAddressDto

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  11.04.2023 20:21
 */

interface DAOPerson {
    suspend fun getAllPeopleAddress(): List<PersonWithAddressDto>
    suspend fun getAllPeople(): List<PersonDto>

    suspend fun getPerson(id: Long): PersonDto?
    suspend fun getPersonAddress(id: Long): PersonWithAddressDto?

    suspend fun addPerson(personDto: PersonDto): PersonDto?
    suspend fun editPerson(id: Long, personDto: PersonDto): Boolean
    suspend fun deletePerson(id: Long): Boolean
}