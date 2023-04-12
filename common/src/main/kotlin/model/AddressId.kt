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
value class AddressId(private val id: Long){
    fun asString() = id

    companion object {
        val NONE = AddressId(0L)
    }
}