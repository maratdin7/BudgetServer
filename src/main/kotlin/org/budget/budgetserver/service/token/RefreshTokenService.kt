package org.budget.budgetserver.service.token

import org.budget.budgetserver.exception.RefreshTokenException
import org.budget.budgetserver.jpa.RefreshTokenEntity
import org.budget.budgetserver.jpa.UserEntity
import org.budget.budgetserver.repository.RefreshTokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RefreshTokenService : AbstractTokenService() {

    @Value("\${jwt.jwtRefreshExpiration}")
    private var daysValid: Long = 0

    @Autowired
    private lateinit var refreshTokenRepository: RefreshTokenRepository

    override fun generateToken(userEntity: UserEntity): String {
        var refreshTokenEntity = RefreshTokenEntity(
            refUserEntity = userEntity,
            token = stringTokenGenerator(),
            expireDate = validBeforeToSqlDate(daysValid)
        )

        refreshTokenEntity = refreshTokenRepository.save(refreshTokenEntity)
        return refreshTokenEntity.token
    }

    fun validateToken(refreshToken: String): RefreshTokenEntity {
        val refreshTokenEntity =
            refreshTokenRepository.findByToken(refreshToken) ?: throw RefreshTokenException("Token not found")

        if (isTokenExpired(refreshTokenEntity.expireDate))
            deleteBadToken(refreshTokenEntity)

        return refreshTokenEntity
    }

    private fun deleteBadToken(refreshTokenEntity: RefreshTokenEntity) {
        refreshTokenRepository.delete(refreshTokenEntity)
        throw RefreshTokenException("The token is rotten")
    }

    @Transactional
    fun deleteAllTokens(userId: Int) {
        refreshTokenRepository.deleteByUserId(userId)
    }

}