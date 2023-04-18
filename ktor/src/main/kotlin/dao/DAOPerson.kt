package ru.shvets.ktor.dao

import ru.shvets.common.model.Person
import ru.shvets.common.model.PersonWithAddress

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  11.04.2023 20:21
 */

interface DAOPerson {
    suspend fun getAllPeopleAddress(): List<PersonWithAddress>
    suspend fun getAllPeople(): List<Person>
    suspend fun getPerson(id: Long): Person?
    suspend fun getPersonAddress(id: Long): PersonWithAddress?
    suspend fun addPerson(person: Person, addressId: Long): Person?
    suspend fun editPerson(id: Long, person: Person, addressId: Long): Boolean
    suspend fun deletePerson(id: Long): Boolean
}