package ru.shvets.common.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import ru.shvets.common.NONE

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  10.04.2023 19:20
 */

private const val MINIMUM_LENGTH = 3

@Serializable
data class Person(
    val id: Long = 0L,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: Instant? = Instant.NONE,
    val contacts: List<Contact>? = null,
)
//{
//    init {
//        require(firstName.length >= MINIMUM_LENGTH) {
//            "FirstName must be at minimum '$MINIMUM_LENGTH' characters."
//        }
//        require(lastName.length >= MINIMUM_LENGTH) {
//            "LastName must be at minimum '$MINIMUM_LENGTH' characters."
//        }
//    }
//}

object PersonTable : Table("people") {
    val id = long("id").autoIncrement()
    val firstName = varchar("first_name", length = 50)
    val lastName = varchar("last_name", 50)
    val dateOfBirth = date("date_of_birth").nullable()

    override val primaryKey = PrimaryKey(id)
}

