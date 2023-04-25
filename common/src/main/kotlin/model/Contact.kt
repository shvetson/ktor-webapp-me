package ru.shvets.common.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  18.04.2023 19:24
 */

@Serializable
data class Contact(
    val id: Long = 0L,
    val typeId: Long = 0L,
    val data: String? = null,
    val personId: Long
)

object ContactTable: LongIdTable("contacts", "id") {
    val typeId = long("type_id")
        .references(ContactTypeTable.id)
    val data = varchar("data", 256).nullable()
    val personId = long("person_id")
        .references(PersonTable.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
}