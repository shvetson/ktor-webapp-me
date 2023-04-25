package ru.shvets.ktor.mapper

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import ru.shvets.common.dto.AddressDto
import ru.shvets.common.model.Address
import ru.shvets.common.model.AddressTable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  22.04.2023 14:37
 */

fun AddressDto.toInsertStatement(personId: Long, statement: InsertStatement<Number>): InsertStatement<Number> =
    statement.also {
        it[AddressTable.personId] = personId
        it[AddressTable.postCode] = this.postCode
        it[AddressTable.region] = this.region
        it[AddressTable.city] = this.city
        it[AddressTable.street] = this.street
        it[AddressTable.house] = this.house
        it[AddressTable.building] = this.building
        it[AddressTable.flat] = this.flat
    }

fun AddressDto.toUpdateStatement(statement: UpdateStatement): UpdateStatement =
    statement.also {
        it[AddressTable.postCode] = this.postCode
        it[AddressTable.region] = this.region
        it[AddressTable.city] = this.city
        it[AddressTable.street] = this.street
        it[AddressTable.house] = this.house
        it[AddressTable.building] = this.building
        it[AddressTable.flat] = this.flat
    }

fun ResultRow.toAddressDto(): AddressDto = AddressDto(
    id = this[AddressTable.id],
    personId = this[AddressTable.personId],
    postCode = this[AddressTable.postCode],
    region = this[AddressTable.region],
    city = this[AddressTable.city],
    street = this[AddressTable.street],
    house = this[AddressTable.house],
    building = this[AddressTable.building],
    flat = this[AddressTable.flat]
)