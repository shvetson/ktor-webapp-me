package ru.shvets.kafka.model.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  10.04.2023 19:20
 */

object People : Table("people") {
    val id = varchar("id", 10)
    val name = varchar("name", length = 50)
    val cityId = (integer("city_id") references Cities.id).nullable()
    val age = integer("age")

    override val primaryKey = PrimaryKey(id)
}

class Person(
    val id: Long,
    var name: String,
    var city: City,
    var age: Int,
)