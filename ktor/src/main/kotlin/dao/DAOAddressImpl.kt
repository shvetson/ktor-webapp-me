package ru.shvets.ktor.dao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import ru.shvets.common.model.*
import ru.shvets.ktor.config.DatabaseFactory.dbQuery

class DAOAddressImpl : DAOAddress {

    override suspend fun addAddress(
        postCode: Int,
        region: String,
        city: String,
        street: String,
        house: String,
        building: String,
        flat: String,
    ): Address? = dbQuery {
        val insertStatement = Addresses.insert {
            it[Addresses.postCode] = postCode
            it[Addresses.region] = region
            it[Addresses.city] = city
            it[Addresses.street] = street
            it[Addresses.house] = house
            it[Addresses.building] = building
            it[Addresses.flat] = flat
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToAddress)
    }

    override suspend fun addAddress(address: Address): Address? = dbQuery {
        val insertStatement = Addresses.insert {
            it[Addresses.postCode] = address.postCode
            it[Addresses.region] = address.region
            it[Addresses.city] = address.city
            it[Addresses.street] = address.street
            it[Addresses.house] = address.house
            it[Addresses.building] = address.building
            it[Addresses.flat] = address.flat
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToAddress)
    }

    override suspend fun editAddress(
        id: Long,
        postCode: Int,
        region: String,
        city: String,
        street: String,
        house: String,
        building: String,
        flat: String,
    ): Boolean = dbQuery {
        Addresses.update({ Addresses.id eq id }) {
            it[Addresses.postCode] = postCode
            it[Addresses.region] = region
            it[Addresses.city] = city
            it[Addresses.street] = street
            it[Addresses.house] = house
            it[Addresses.building] = building
            it[Addresses.flat] = flat
        } > 0
    }

    override suspend fun deleteAddress(id: Long): Boolean = dbQuery {
        Addresses.deleteWhere { Addresses.id eq id } > 0
    }

    private fun resultRowToAddress(
        row: ResultRow,
    ): Address {
        return Address(
            id = row[Addresses.id],
            postCode = row[Addresses.postCode],
            region = row[Addresses.region],
            city = row[Addresses.city],
            street = row[Addresses.street],
            house = row[Addresses.house],
            building = row[Addresses.building],
            flat = row[Addresses.flat]
        )
    }
}

val daoAddress: DAOAddress = DAOAddressImpl()