package ru.shvets.ktor.utils

import io.ktor.http.*
import io.ktor.server.util.*
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toLocalDate
import ru.shvets.common.dto.AddressDto
import ru.shvets.common.dto.ContactDto
import ru.shvets.common.dto.PersonDto
import ru.shvets.common.model.Address
import ru.shvets.common.model.Person
import ru.shvets.common.util.fromKotlinInstantToLocalDateString
import ru.shvets.common.util.fromLocalDateToKotlinInstant
import ru.shvets.common.util.fromStringToLocalDate
import ru.shvets.ktor.config.Log.logger

fun createPerson(formParameters: Parameters): PersonDto {
    return PersonDto(
        firstName = formParameters.getOrFail("firstName"),
        lastName = formParameters.getOrFail("lastName"),
        dateOfBirth = formParameters.getOrFail("dateOfBirth"),
    )
}

fun createAddress(formParameters: Parameters): AddressDto {
    return AddressDto(
        id = formParameters.getOrFail("id").toLongOrNull() ?: throw Exception("Invalid address id"),
        postCode = formParameters.getOrFail("postCode").toIntOrNull() ?: throw Exception("Invalid post code"),
        region = formParameters.getOrFail("region"),
        city = formParameters.getOrFail("city"),
        street = formParameters.getOrFail("street"),
        house = formParameters.getOrFail("house"),
        building = formParameters.getOrFail("building"),
        flat = formParameters.getOrFail("flat"),
    )
}

fun createContact(formParameters: Parameters): ContactDto {
    return ContactDto(
        typeId = formParameters.getOrFail("typeId").toLongOrNull() ?: throw Exception("Invalid contact type id"),
        data = formParameters.getOrFail("data"),
    )
}