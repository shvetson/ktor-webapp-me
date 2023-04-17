package ru.shvets.common.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Table
import ru.shvets.common.model.People.autoIncrement

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  09.04.2023 22:52
 */

@Serializable
data class Address(
    val id: Long = 0L,
    val postCode: Int,
    val region: String,
    val city: String,
    val street: String,
    val house: String,
    val building: String? = null,
    val flat: String,
)

object Addresses : Table("addresses") {
    val id = long("id").autoIncrement()
    val postCode = integer("post_code")
    val region = varchar("region", 50)
    val city = varchar("city", 50)
    val street = varchar("street", 50)
    val house = varchar("house", 10)
    val building = varchar("building", 10).nullable()
    val flat = varchar("flat", 10)

    override val primaryKey = PrimaryKey(id)
}