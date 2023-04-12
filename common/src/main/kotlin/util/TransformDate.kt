package ru.shvets.common.util

import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  12.04.2023 17:58
 */

// Kotlin Instant -> Java Date String
fun Instant.fromKotlinInstantToLocalDateString(): String {
    return this.toLocalDateTime(TimeZone.currentSystemDefault()).date.toString()
}

// Java LocalDate -> Kotlin Instant
fun LocalDate.fromLocalDateToKotlinInstant(): Instant {
    return this.toKotlinLocalDate().atStartOfDayIn(TimeZone.currentSystemDefault())
}

// Java Date String (yyyy-MM-dd) -> Java LocalDate
fun String.fromStringToLocalDate(): LocalDate {
    val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return LocalDate.ofInstant(df.parse(this).toInstant(), ZoneId.systemDefault())
}