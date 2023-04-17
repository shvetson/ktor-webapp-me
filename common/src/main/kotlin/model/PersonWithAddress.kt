package ru.shvets.common.model

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  14.04.2023 23:32
 */

data class PersonWithAddress(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val phone: String,
    val postCode: Int? = null,
    val region: String? = null,
    val city: String? = null,
    val street: String? = null,
    val house: String? = null,
    val building: String? = null,
    val flat: String? = null,
)