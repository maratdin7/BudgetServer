package org.budget.budgetserver.service.token

import org.budget.budgetserver.jpa.UserEntity
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import java.sql.Date as SqlDate
import java.util.Date as UtilDate

abstract class AbstractTokenService {

    protected fun validBeforeToSqlDate(daysValid: Long): SqlDate = SqlDate.valueOf(
        validBefore(daysValid).toLocalDate()
    )

    protected fun validBeforeToUtilDate(daysValid: Long): UtilDate =
        UtilDate.from(validBefore(daysValid).toInstant())

    protected fun validBefore(daysValid: Long): ZonedDateTime = LocalDate.now()
        .plusDays(daysValid)
        .atStartOfDay(ZoneId.systemDefault())

    protected fun stringTokenGenerator(): String = UUID.randomUUID().toString()

    protected fun intTokenGenerator(bound: Int): Int = Random().nextInt(bound)

    protected fun isTokenExpired(expireDate: SqlDate) =
        expireDate.toLocalDate() < LocalDate.now()

    abstract fun generateToken(userEntity: UserEntity): String

}