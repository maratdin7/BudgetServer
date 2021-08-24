package org.budget.budgetserver.service.impl

import org.budget.budgetserver.event.OnRegistrationCompleteEvent
import org.budget.budgetserver.event.OnResetPasswordEvent
import org.budget.budgetserver.exception.UserAlreadyActivatedException
import org.budget.budgetserver.jpa.UserEntity
import org.budget.budgetserver.service.AccessRefreshTokens
import org.budget.budgetserver.service.AuthService
import org.budget.budgetserver.service.UserService
import org.budget.budgetserver.service.token.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl : AuthService {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var jwtTokenService: JwtService

    @Autowired
    private lateinit var refreshTokenService: RefreshTokenService

    @Autowired
    private lateinit var confirmationTokenService: ConfirmationTokenService

    @Autowired
    private lateinit var resetPasswordTokenService: ResetPasswordTokenService

    @Autowired
    private lateinit var eventPublisher: ApplicationEventPublisher

    override fun signIn(login: String, pass: String): AccessRefreshTokens {
        val userEntity = userService.findUserAndCheck(login, pass)
        return accessRefreshTokens(userEntity)
    }

    override fun signUp(email: String, pass: String): AccessRefreshTokens {
        val userEntity = userService.createUserEntity(email, pass)

        eventPublisher.publishEvent(OnRegistrationCompleteEvent(userEntity))
        return accessRefreshTokens(userEntity)
    }

    override fun requestAccountConfirmation(email: String) {
        val userEntity = userService.findUserEntityByName(email)

        eventPublisher.publishEvent(OnRegistrationCompleteEvent(userEntity))
    }

    override fun generateAccessToken(email: String, refreshToken: String): AccessRefreshTokens {
        val userEntity = refreshTokenService.validateUserEntityByToken(email, refreshToken)
        return accessRefreshTokens(userEntity)
    }

    override fun accountConfirmation(email: String, token: String) {
        val userEntity = userService.findUserEntityByName(email)

        if (userEntity.enable)
            throw UserAlreadyActivatedException()

        confirmationTokenService.validateToken(userEntity.id, token)

        userEntity.enable = true
        userService.updateUserEntity(userEntity)
    }

    override fun resetPassword(email: String) {
        val userEntity = userService.findUserEntityByName(email)

        eventPublisher.publishEvent(OnResetPasswordEvent(userEntity))
    }

    override fun confirmResetPassword(email: String, token: Int): String {
        val userEntity = resetPasswordTokenService.validateUserEntityByToken(email, token)
        return jwtTokenService.generateToken(userEntity)
    }

    private fun <T> AbstractTokenService<T>.validateUserEntityByToken(email: String, token: T): UserEntity {
        val userEntity = userService.findUserEntityByName(email)
        this.validateToken(userEntity.id, token)

        return userEntity
    }

    private fun accessRefreshTokens(userEntity: UserEntity) =
        AccessRefreshTokens(jwtTokenService, refreshTokenService, userEntity)
}