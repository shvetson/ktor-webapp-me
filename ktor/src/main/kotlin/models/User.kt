package ru.shvets.ktor.models

import kotlinx.serialization.Serializable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  26.03.2023 13:55
 */

@Serializable
data class User(
    val id: Int,
    val name: String,
)