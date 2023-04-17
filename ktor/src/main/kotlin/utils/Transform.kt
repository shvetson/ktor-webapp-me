package ru.shvets.ktor.utils

import io.ktor.http.*
import io.ktor.server.util.*
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDate
import ru.shvets.common.model.Address
import ru.shvets.common.model.Person
import ru.shvets.common.util.fromLocalDateToKotlinInstant
import ru.shvets.common.util.fromStringToLocalDate

fun createPerson(formParameters: Parameters): Person {
    return Person(
        firstName = formParameters.getOrFail("firstName"),
        lastName = formParameters.getOrFail("lastName"),
        dateOfBirth = formParameters.getOrFail("dateOfBirth").fromStringToLocalDate().fromLocalDateToKotlinInstant(),
        phone = formParameters.getOrFail("phone"),
        addressId = 0L
    )
}

fun createAddress(formParameters: Parameters): Address {
    return Address(
        postCode = formParameters.getOrFail("postCode").toIntOrNull() ?: 0,
        region = formParameters.getOrFail("region"),
        city = formParameters.getOrFail("city"),
        street = formParameters.getOrFail("street"),
        house = formParameters.getOrFail("house"),
        building = formParameters.getOrFail("building"),
        flat = formParameters.getOrFail("flat"),
    )
}