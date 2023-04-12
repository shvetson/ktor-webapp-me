package ru.shvets.common.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNotNull
import org.jetbrains.exposed.sql.Table

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  10.04.2023 19:05
 */

@Serializable
data class City(
    val id: Long,
    val name: String,
)

object Cities : LongIdTable() {
    val name = varchar("name", 50).isNotNull()
}