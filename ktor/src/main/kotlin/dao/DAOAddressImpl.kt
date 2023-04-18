package ru.shvets.ktor.dao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.StatementContext
import ru.shvets.common.model.*
import ru.shvets.ktor.config.DatabaseFactory.dbQuery

class DAOAddressImpl : DAOAddress {
    override suspend fun addAddress(address: Address): Address? = dbQuery {
        val insertStatement = Addresses.insert {
            it[postCode] = address.postCode
            it[region] = address.region
            it[city] = address.city
            it[street] = address.street
            it[house] = address.house
            it[building] = address.building
            it[flat] = address.flat
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToAddress)
    }

    override suspend fun editAddress(id: Long, address: Address): Boolean = dbQuery {
        Addresses.update({ Addresses.id eq id }) {
            it[postCode] = address.postCode
            it[region] = address.region
            it[city] = address.city
            it[street] = address.street
            it[house] = address.house
            it[building] = address.building
            it[flat] = address.flat
        } > 0
    }

    override suspend fun deleteAddress(id: Long): Boolean = dbQuery {
        Addresses.deleteWhere { Addresses.id eq id } > 0
    }

    private fun resultRowToAddress(row: ResultRow): Address {
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