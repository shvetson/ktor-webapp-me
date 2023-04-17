package ru.shvets.ktor.dao

import ru.shvets.common.model.Address

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  15.04.2023 19:38
 */

interface DAOAddress {
    suspend fun addAddress(
        postCode: Int,
        region: String,
        city: String,
        street: String,
        house: String,
        building: String,
        flat: String,
    ): Address?

    suspend fun addAddress(address: Address): Address?

    suspend fun editAddress(
        id: Long,
        postCode: Int,
        region: String,
        city: String,
        street: String,
        house: String,
        building: String,
        flat: String
    ): Boolean

    suspend fun deleteAddress(id: Long): Boolean
}