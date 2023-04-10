package ru.shvets.ktor.dao

import ru.shvets.ktor.models.Article

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  26.03.2023 10:40
 */

interface DAOFacade {
    suspend fun allArticles(): List<Article>
    suspend fun article(id: Int): Article?
    suspend fun addNewArticle(title: String, body: String): Article?
    suspend fun editArticle(id: Int, title: String, body: String): Boolean
    suspend fun deleteArticle(id: Int): Boolean
}