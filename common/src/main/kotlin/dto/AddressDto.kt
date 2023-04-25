package ru.shvets.common.dto

import kotlinx.serialization.Serializable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  22.04.2023 14:41
 */

@Serializable
data class AddressDto(
    val id: Long = 0L,
    val personId: Long = 0L,
    val postCode: Int,
    val region: String,
    val city: String,
    val street: String,
    val house: String,
    val building: String? = null,
    val flat: String,
)
