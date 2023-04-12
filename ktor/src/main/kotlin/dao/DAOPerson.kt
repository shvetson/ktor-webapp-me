package ru.shvets.ktor.dao

import ru.shvets.common.model.Person

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  11.04.2023 20:21
 */

interface DAOPerson {
    suspend fun getAllPeople(): List<Person>
    suspend fun getPerson(id: Long): Person?
    suspend fun addPerson(firstName: String, lastName: String, dateOfBirth: String, phone: String,): Person?
    suspend fun editPerson(id: Long, firstName: String, lastName: String, dateOfBirth: String, phone: String,): Boolean
    suspend fun deletePerson(id: Long): Boolean
}