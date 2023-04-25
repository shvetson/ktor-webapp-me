package ru.shvets.common.dto

import kotlinx.serialization.Serializable
import ru.shvets.common.model.Contact
import ru.shvets.common.model.Person
import ru.shvets.common.util.fromKotlinInstantToLocalDateString

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  12.04.2023 13:41
 */

@Serializable
data class PersonDto(
    val id: Long? = 0L,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String? = null,
    val addresses: List<AddressDto>? =  null,
    val contacts: List<ContactDto>? = null
)