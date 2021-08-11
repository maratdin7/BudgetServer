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
class PasswordAuthenticationException : RuntimeException()

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