package ru.shvets.ktor.mapper

import org.jetbrains.exposed.sql.ResultRow
import ru.shvets.common.dto.ContactDto
import ru.shvets.common.model.ContactTable
import ru.shvets.common.model.ContactType
import ru.shvets.common.model.ContactTypeTable

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  22.04.2023 15:24
 */

fun ResultRow.toContactDto(): ContactDto = ContactDto(
    id = this[ContactTable.id].value,
    typeId = this[ContactTypeTable.id].value, //
    type = this[ContactTypeTable.name],
    data = this[ContactTable.data],
)

fun ResultRow.toContactsType(): ContactType = ContactType(
    id = this[ContactTypeTable.id].value,
    name = this[ContactTypeTable.name]
)