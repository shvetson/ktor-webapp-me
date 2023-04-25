package ru.shvets.common.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.timestamp

/**
 * @author  Oleg Shvets
 * @version 1.0
 * @date  26.03.2023 13:55
 */

//private const val MINIMUM_PASSWORD_LENGTH = 16
//private const val MINIMUM_AGE_YEARS = 18L
//private const val PASSWORD_REGEX_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$"

@Serializable
data class User(
    val id: Long,
    val username: String,
    val password: String,
    val email: String,
    val isAccountNonExpired: Boolean = false,
    val isAccountNonLocked: Boolean = false,
    val isCredentialsNonExpired: Boolean = false,
    val isEnabled: Boolean = true,
    val personId: PersonId = PersonId.NONE,
    val createdAt: Instant = Clock.System.now(),
    val updatedAt: Instant = Clock.System.now(),
)
//{
//    init {
//        val requiredDate = LocalDate.now().minusYears(MINIMUM_AGE_YEARS)
//        require(birthdate.isBefore(requiredDate)) {
//            "Birthdate must be before '$requiredDate'."
//        }
//        require(firstName.length >= MINIMUM_PASSWORD_LENGTH) {
//            "Password must be at minimum '$MINIMUM_PASSWORD_LENGTH' characters."
//        }
//        require(password.matches(Regex(PASSWORD_REGEX_PATTERN))) {
//            "Password must contain at minimum one lowercase, one uppercase, one special character and one digit."
//        }
//    }
//}

object UserTable: LongIdTable("users") {
    val username = varchar("username", 50)
    val password = varchar("password", 100)
    val email = varchar("email", 30)
    val isAccountNonExpired = bool("is_account_non_expired")
    val isAccountNonLocked = bool("is_account_non_locked")
    val isEnabled = bool("is_enabled")
    val isCredentialsNonExpired = bool("is_credentials_non_expired")
    val personId = long("person_id").references(PersonTable.id)
    val createdAt = timestamp("created_at").default(Clock.System.now().toJavaInstant())
    val updatedAt = timestamp("updated_at").default(Clock.System.now().toJavaInstant())
}