package org.budget.budgetserver.service

import org.budget.budgetserver.event.OnRegistrationCompleteEvent
import org.budget.budgetserver.event.OnResetPasswordEvent
import org.budget.budgetserver.exception.PasswordAuthenticationException
import org.budget.budgetserver.exception.UserAlreadyActivatedException
import org.budget.budgetserver.exception.UsernameAlreadyExistException
import org.budget.budgetserver.exception.UsernameNotFoundException
import org.budget.budgetserver.jpa.UserEntity
import org.budget.budgetserver.jwt.CustomUserDetails
import org.budget.budgetserver.repository.UserRepository
import org.budget.budgetserver.service.token.ConfirmationTokenService
import org.budget.budgetserver.service.token.JwtTokenService
import org.budget.budgetserver.service.token.RefreshTokenService
import org.budget.budgetserver.service.token.ResetPasswordTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AuthServiceImpl(private val userRepository: UserRepository) {

    @Autowired
    private lateinit var jwtTokenService: JwtTokenService

    @Autowired
    private lateinit var refreshTokenService: RefreshTokenService

    @Autowired
    private lateinit var confirmationTokenService: ConfirmationTokenService

    @Autowired
    private lateinit var resetPasswordTokenService: ResetPasswordTokenService

    @Autowired
    private lateinit var eventPublisher: ApplicationEventPublisher

    @Autowired
    private lateinit var encode: PasswordEncoder

    private fun getUserEntity(): UserEntity {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as CustomUserDetails
        return userDetails.getUserEntity()
    }

    data class AccessRefreshTokens(val accessToken: String, val refreshToken: String)

    private fun String.encodePass(encodedPass: String) =
        encode.matches(this, encodedPass)


    fun findUserByName(login: String): UserEntity {
        return userRepository.findUserEntityByName(login)
            ?: throw UsernameNotFoundException("User with name $login not found")
    }

    private fun generateTokens(userEntity: UserEntity): AccessRefreshTokens {
        val accessToken = jwtTokenService.generateToken(userEntity)
        val refreshToken = refreshTokenService.generateToken(userEntity)
        return AccessRefreshTokens(accessToken, refreshToken)
    }

    fun signIn(login: String, pass: String): AccessRefreshTokens {
        val userEntity = findUserByName(login)
        if (pass.encodePass(userEntity.password)) {
            return generateTokens(userEntity)
        } else
            throw PasswordAuthenticationException()
    }

    fun signUp(login: String, pass: String): AccessRefreshTokens {
        if (userRepository.existsByName(login))
            throw UsernameAlreadyExistException()

        val userEntity = userRepository.save(
            UserEntity(
                name = login,
                password = encode.encode(pass)
            )
        )

        eventPublisher.publishEvent(OnRegistrationCompleteEvent(userEntity))
        return generateTokens(userEntity)
    }

    fun requestAccountConfirmation() {
        eventPublisher.publishEvent(OnRegistrationCompleteEvent(getUserEntity()))
    }

    fun signOut() {
        SecurityContextHolder.clearContext()
    }

    fun refreshToken(refreshToken: String): String {
        val refreshTokenEntity = refreshTokenService.validateToken(refreshToken)
        val userEntity = refreshTokenEntity.refUserEntity!!
        return jwtTokenService.generateToken(userEntity)
    }

    fun accountConfirmation(userId: Int, token: String) {
        confirmationTokenService.validateToken(userId, token)

        val userEntity = userRepository.findByIdOrNull(userId)!!
        if (userEntity.enable)
            throw UserAlreadyActivatedException()

        userEntity.enable = true
        userRepository.save(userEntity)
    }

    fun resetPassword(email: String) {
        val userEntity = findUserByName(email)

        eventPublisher.publishEvent(OnResetPasswordEvent(userEntity))
    }

    fun confirmResetPassword(email: String, token: Int): String {
        val userEntity = findUserByName(email)
        resetPasswordTokenService.validateToken(userEntity, token)

        return jwtTokenService.generateToken(userEntity)
    }

    fun changePassword(pass: String): AccessRefreshTokens {
        val userEntity = getUserEntity()

        userEntity.password = encode.encode(pass)
        userRepository.save(userEntity)

        refreshTokenService.deleteAllTokens(userEntity.id)
        return generateTokens(userEntity)
    }

}