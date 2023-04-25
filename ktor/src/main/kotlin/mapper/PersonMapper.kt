package ru.shvets.ktor.mapper

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import ru.shvets.common.dto.PersonDto
import ru.shvets.common.model.AddressTable
import ru.shvets.common.model.ContactTable
import ru.shvets.common.model.ContactTypeTable
import ru.shvets.common.model.PersonTable
import ru.shvets.common.util.fromKotlinInstantToLocalDateString
import ru.shvets.common.util.fromLocalDateToKotlinInstant

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  22.04.2023 15:27
 */

fun ResultRow.toPersonDto(): PersonDto = PersonDto(
    id = this[PersonTable.id],
    firstName = this[PersonTable.firstName],
    lastName = this[PersonTable.lastName],
    dateOfBirth = this[PersonTable.dateOfBirth]?.fromLocalDateToKotlinInstant()?.fromKotlinInstantToLocalDateString() ?: "",
    addresses = this[PersonTable.id].let {
        (AddressTable)
            .select {AddressTable.personId eq it}
            .map { it.toAddressDto() }
    },
    contacts = this[PersonTable.id].let {
        (ContactTable leftJoin ContactTypeTable)
            .select { ContactTable.personId eq it }
            .orderBy(ContactTable.typeId)
            .map { it.toContactDto() }
    }
)