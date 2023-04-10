package ru.shvets.ktor.models

import kotlinx.serialization.Serializable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  24.03.2023 09:10
 */

@Serializable
data class Customer(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
)

//val customerStorage = mutableListOf<Customer>()
val customerStorage = mutableListOf<Customer>(
    Customer("1", "Oleg", "Shvets", "oleg@mail.ru" ),
    Customer("2", "Pavel", "Sitnov", "pavel@mail.ru" ),
    Customer("3", "Denis", "Fedosenkov", "denis@mail.ru" ),
    Customer("4", "Victor", "Ilin", "victor@mail.ru" ),
)