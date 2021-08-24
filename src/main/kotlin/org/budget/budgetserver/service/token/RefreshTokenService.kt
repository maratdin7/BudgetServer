package org.budget.budgetserver.service.token

import org.budget.budgetserver.exception.RefreshTokenException
import org.budget.budgetserver.jpa.RefreshTokenEntity
import org.budget.budgetserver.jpa.UserEntity
import org.budget.budgetserver.repository.RefreshTokenRepository
import org.budget.budgetserver.service.token.DateConverter.toSqlDate
import org.budget.budgetserver.service.token.DateConverter.validBeforeToSqlDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class RefreshTokenService : AbstractTokenService<String>() {

    @Value("\${jwt.jwtRefreshExpiration}")
    private var daysValid: Long = 0

    @Autowired
    private lateinit var refreshTokenRepository: RefreshTokenRepository

    override fun generateToken(userEntity: UserEntity): String {
        val token = stringTokenGenerator()
        val refreshTokenEntity = RefreshTokenEntity(
            refUserEntity = userEntity,
            token = token,
            expireDate = validBeforeToSqlDate(daysValid)
        )

        refreshTokenRepository.save(refreshTokenEntity)
        return token
    }

    @Transactional
    override fun validateToken(userId: Int, token: String): Boolean {
        val isValid = refreshTokenRepository.deleteValidToken(userId, token, LocalDate.now().toSqlDate())

        if (isValid == 0) {
            refreshTokenRepository.deleteByUserIdAndToken(userId, token)
            throw RefreshTokenException("The token is rotten")
        }

        return true
    }

    @Transactional
    fun deleteToken(userId: Int, token: String) {
        refreshTokenRepository.deleteByUserIdAndToken(userId, token)
    }

    @Transactional
    fun deleteAllTokens(userId: Int) {
        refreshTokenRepository.deleteByUserId(userId)
    }

}