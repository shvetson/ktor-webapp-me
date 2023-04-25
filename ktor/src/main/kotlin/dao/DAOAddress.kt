package ru.shvets.ktor.dao

import ru.shvets.common.dto.AddressDto
import ru.shvets.common.model.Address

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  15.04.2023 19:38
 */

interface DAOAddress {

    suspend fun getAddress(personId: Long): AddressDto?
    suspend fun addAddress(personId: Long, addressDto: AddressDto): Address?
    suspend fun editAddress(addressDto: AddressDto): Boolean
    suspend fun deleteAddress(personId: Long): Boolean
}