package ru.shvets.ktor.dao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import ru.shvets.common.dto.AddressDto
import ru.shvets.common.model.*
import ru.shvets.ktor.config.DatabaseFactory.dbQuery
import ru.shvets.ktor.mapper.toAddressDto
import ru.shvets.ktor.mapper.toInsertStatement
import ru.shvets.ktor.mapper.toUpdateStatement

class DAOAddressImpl : DAOAddress {
    override suspend fun getAddress(personId: Long): AddressDto? = dbQuery {
        AddressTable
            .select(AddressTable.personId eq personId)
            .map {it.toAddressDto()}
            .singleOrNull()
    }

    override suspend fun addAddress(personId: Long, addressDto: AddressDto): Address? = dbQuery {
        val insertStatement = AddressTable.insert { addressDto.toInsertStatement(personId, it) }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToAddress)
            ?: throw NoSuchElementException("Error saving address. Statement result is null.")
    }

    override suspend fun editAddress(addressDto: AddressDto): Boolean = dbQuery {
        AddressTable.update({ AddressTable.id eq addressDto.id }) { addressDto.toUpdateStatement(it) } > 0
    }

    override suspend fun deleteAddress(personId: Long): Boolean = dbQuery {
        AddressTable.deleteWhere { AddressTable.personId eq personId } > 0
    }

    private fun resultRowToAddress(row: ResultRow): Address {
        return Address(
            id = row[AddressTable.id],
            personId = row[AddressTable.personId],
            postCode = row[AddressTable.postCode],
            region = row[AddressTable.region],
            city = row[AddressTable.city],
            street = row[AddressTable.street],
            house = row[AddressTable.house],
            building = row[AddressTable.building],
            flat = row[AddressTable.flat]
        )
    }
}

val daoAddress: DAOAddress = DAOAddressImpl()