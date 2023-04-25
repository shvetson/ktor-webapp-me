package ru.shvets.common.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.LongIdTable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  18.04.2023 19:25
 */

@Serializable
data class ContactType(
    val id: Long = 0L,
    val name: String
)

object ContactTypeTable: LongIdTable("contact_types", "id") {
    val name = varchar("name", 20)
}