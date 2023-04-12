package ru.shvets.common.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.LongIdTable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  09.04.2023 22:52
 */

@Serializable
data class Address(
    val id: Long,
    val postCode: Int,
    val region: String,
    val cityId: Long,
    val street: String,
    val house: String,
    val building: String,
    val flat: String,
)

object Addresses: LongIdTable("addresses") {
    val postCode = integer("post_code")
    val region = varchar("region", 50)
    val cityId = long("city_id").references(Cities.id)
    val street = varchar("street", 50)
    val house = varchar("varchar", 10)
    val building = varchar("building", 10)
    val flat = varchar("flat", 10)
}