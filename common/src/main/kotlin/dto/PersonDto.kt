package ru.shvets.common.dto

import kotlinx.serialization.Serializable
import ru.shvets.common.model.Person
import ru.shvets.common.util.fromKotlinInstantToLocalDateString

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  12.04.2023 13:41
 */

@Serializable
data class PersonDto(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val phone: String,
)

fun Person.toDto(): PersonDto =
    PersonDto(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        dateOfBirth = this.dateOfBirth?.fromKotlinInstantToLocalDateString() ?: "",
        phone = this.phone ?: ""
    )