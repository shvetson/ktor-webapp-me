package ru.shvets.ktor.dao

import ru.shvets.common.model.Address

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  15.04.2023 19:38
 */

interface DAOAddress {
    suspend fun addAddress(address: Address): Address?
    suspend fun editAddress(id: Long, address: Address): Boolean
    suspend fun deleteAddress(id: Long): Boolean
}