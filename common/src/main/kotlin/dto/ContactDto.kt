package ru.shvets.common.dto

import kotlinx.serialization.Serializable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  21.04.2023 23:48
 */

@Serializable
data class ContactDto(
    val id: Long = 0L,
    val typeId: Long = 0L,
    val type: String? = null, // возможно надо исключить?
    val data: String? = null,
)