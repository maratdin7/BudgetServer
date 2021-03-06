package org.budget.budgetserver.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "There is no such user")
class UsernameNotFoundException(override var message: String = "") : RuntimeException()

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User already exist")
class UsernameAlreadyExistException(override var message: String = "") : RuntimeException()

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Verification token not found")
class VerificationTokenNotFoundException(override var message: String = "") : RuntimeException()

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User is already activated")
class UserAlreadyActivatedException(override var message: String = "") : RuntimeException()

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Invalid password")
class WrongPasswordException : RuntimeException()

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Refresh token was expired. Please make a new signin request")
class RefreshTokenException(override val message: String = "") : RuntimeException()

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "The access token is not valid")
class AccessTokenException(override val message: String = "") : RuntimeException()

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Attempts to validate the token have ended")
class AttemptsEndedException(override val message: String = "") : RuntimeException()

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "The reset password token is not valid")
class ResetPasswordTokenException(override val message: String = "") : RuntimeException()

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "The password reset token does not exist for the user")
class ResetPasswordTokenNotFoundException(override val message: String = "") : RuntimeException()

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "The validity period has expired")
class TokenExpiredException(override val message: String = "") : RuntimeException()

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Security context auth not exist")
class SecurityContextAuthNotExistException : RuntimeException()

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "The group already exists")
class GroupAlreadyExistsException(override val message: String = "") : RuntimeException()

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "The user is not a member of the group")
class UserIsNotMemberOfGroupException(override val message: String = "") : RuntimeException()

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "The user is already a member of the group")
class UserIsAlreadyMemberOfGroupException(override val message: String = "") : RuntimeException()

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The group was not found")
class GroupNotFoundException(override val message: String = "") : RuntimeException()

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Token not valid")
class UserToGroupTokenException(override val message: String = "") : RuntimeException()

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "The cash account was not found")
class CashAccountNotFoundException(override val message: String = "") : RuntimeException()

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "The category already exists")
class CategoryAlreadyExistsException(override val message: String = "") : RuntimeException()

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "The category was not found")
class CategoryNotFoundException(override val message: String = "") : RuntimeException()

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Sql request exception")
class RequestErrorException(override val message: String = "") : RuntimeException()

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "The date format exception")
class DateFormatException(override val message: String = "") : RuntimeException()