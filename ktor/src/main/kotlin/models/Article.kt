package ru.shvets.ktor.models

import org.jetbrains.exposed.sql.Table

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  26.03.2023 10:32
 */

data class Article(val id: Int, val title: String, val body: String)

object Articles : Table(name = "articles") {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 128)
    val body = varchar("body", 1024)

    override val primaryKey = PrimaryKey(id)
}