package org.budget.budgetserver.service.token

import org.budget.budgetserver.exception.*
import org.budget.budgetserver.jpa.ResetPasswordTokenEntity
import org.budget.budgetserver.jpa.UserEntity
import org.budget.budgetserver.repository.ResetPasswordTokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class ResetPasswordTokenService : AbstractTokenService() {

    @Value("\${reset.password.token.expiration}")
    private var daysValid: Long = 0

    @Value("\${reset.password.token.attempt}")
    private var attempt: Int = 0

    @Value("\${reset.password.token.size}")
    private var tokenSize: Int = 0

    @Autowired
    private lateinit var resetPasswordTokenRepository: ResetPasswordTokenRepository

    private fun findTokenIdByUserId(userId: Int): Int =
        resetPasswordTokenRepository.findByUserId(userId)?.id ?: 0

    override fun generateToken(userEntity: UserEntity): String {

        var resetPasswordTokenEntity = ResetPasswordTokenEntity(
            id = findTokenIdByUserId(userEntity.id),
            refUserEntity = userEntity,
            token = intTokenGenerator(tokenSize),
            expireDate = validBeforeToSqlDate(daysValid),
            attempt = attempt
        )

        resetPasswordTokenEntity = resetPasswordTokenRepository.save(resetPasswordTokenEntity)
        return resetPasswordTokenEntity.token.toString()
    }

    private fun ResetPasswordTokenEntity.checkToken(token: Int) {
        if (isTokenExpired(expireDate))
            throw TokenExpiredException()

        if (this.token != token)
            this.updateAttempt()
    }

    private fun ResetPasswordTokenEntity.updateAttempt() {
        attempt--
        if (attempt == 0) {
            throw AttemptsEndedException("Attempts ended request a new code")
        } else {
            resetPasswordTokenRepository.save(this)
            throw ResetPasswordTokenException("There are $attempt attempts left")
        }
    }

    fun validateToken(userEntity: UserEntity, token: Int): Boolean {
        val resetPasswordTokenEntity =
            resetPasswordTokenRepository.findByUserId(userEntity.id)
                ?: throw ResetPasswordTokenNotFoundException("Token not found. Try creating a new token")

        resetPasswordTokenEntity.checkToken(token)

        resetPasswordTokenRepository.delete(resetPasswordTokenEntity)
        return true
    }
}