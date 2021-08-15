package org.budget.budgetserver.service.token

import org.budget.budgetserver.jpa.UserEntity
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import java.sql.Date as SqlDate
import java.util.Date as UtilDate

object DateConverter {
    fun validBeforeToSqlDate(daysValid: Long): java.sql.Date =
        validBefore(daysValid).toLocalDate().toSqlDate()


    fun validBeforeToUtilDate(hourValid: Long, daysValid: Long = 0): java.util.Date =
        UtilDate.from(validBefore(daysValid, hourValid).toInstant())

    private fun validBefore(daysValid: Long, hourValid: Long = 0): ZonedDateTime = LocalDate.now()
        .plusDays(daysValid)
        .atStartOfDay(ZoneId.systemDefault()).plusHours(hourValid)

    fun LocalDate.toSqlDate(): SqlDate = SqlDate.valueOf(this)
}

abstract class AbstractTokenService<T> {

    protected fun stringTokenGenerator(): String = UUID.randomUUID().toString()

    protected fun intTokenGenerator(bound: Int): Int = Random().nextInt(bound)

    protected fun isTokenExpired(expireDate: SqlDate) =
        expireDate.toLocalDate() < LocalDate.now()

    abstract fun generateToken(userEntity: UserEntity): T

    abstract fun validateToken(userId: Int, token: T): Boolean
}