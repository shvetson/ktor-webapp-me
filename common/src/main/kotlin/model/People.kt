package ru.shvets.common.model

import kotlinx.datetime.Instant
import kotlinx.datetime.toInstant
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.javatime.timestamp
import ru.shvets.common.NONE

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  10.04.2023 19:20
 */

@Serializable
data class Person(
    val id: Long = 0L,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: Instant? = Instant.NONE,
    val phone: String? = null,
    val addressId: Long? = null
)

object People : Table("people") {
    val id = long("id").autoIncrement()
    val firstName = varchar("first_name", length = 50)
    val lastName = varchar("last_name", 50)
    val dateOfBirth = date("date_of_birth").nullable()
    val phone = varchar("phone", 20).nullable()
    val addressId = long("address_id").references(Addresses.id).nullable()

    override val primaryKey = PrimaryKey(id)
}