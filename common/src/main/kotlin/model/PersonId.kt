package ru.shvets.common.model

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  13.03.2023 13:12
 */

@JvmInline
@Serializable
value class PersonId(private val id: Long){
    fun asString() = id

    companion object {
        val NONE = PersonId(0L)
    }
}