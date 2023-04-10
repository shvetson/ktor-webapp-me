package ru.shvets.ktor.dao

import kotlinx.coroutines.runBlocking
import ru.shvets.ktor.config.DatabaseFactory.dbQuery
import ru.shvets.ktor.models.Article
import ru.shvets.ktor.models.Articles
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOFacadeImpl : DAOFacade {
    override suspend fun allArticles(): List<Article> {
        return dbQuery {
            Articles
                .selectAll()
                .map(::resultRowToArticle)
        }
    }

    override suspend fun article(id: Int): Article? {
        return dbQuery {
            Articles
                .select { Articles.id eq id }
                .map(::resultRowToArticle)
                .singleOrNull()
        }
    }

    override suspend fun addNewArticle(title: String, body: String): Article? = dbQuery {
        val insertStatement = Articles.insert {
            it[Articles.title] = title
            it[Articles.body] = body
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToArticle)
    }

    override suspend fun editArticle(id: Int, title: String, body: String): Boolean = dbQuery {
        Articles.update({ Articles.id eq id }) {
            it[Articles.title] = title
            it[Articles.body] = body
        } > 0
    }

    override suspend fun deleteArticle(id: Int): Boolean = dbQuery {
        Articles.deleteWhere { Articles.id eq id } > 0
    }

    private fun resultRowToArticle(row: ResultRow): Article {
        return Article(
            id = row[Articles.id],
            title = row[Articles.title],
            body = row[Articles.body],
        )
    }
}

val dao: DAOFacade = DAOFacadeImpl().apply {
    runBlocking {
        if(allArticles().isEmpty()) {
            addNewArticle("The drive to develop!", "...it's what keeps me going.")
        }
    }
}