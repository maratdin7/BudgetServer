package org.budget.budgetserver.service.token

import org.budget.budgetserver.jpa.UserEntity
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtTokenService : AbstractTokenService() {
    private val logger = LoggerFactory.getLogger(JwtTokenService::class.java)

    @Value("\${jwt.secret}")
    private lateinit var jwtSecret: String

    @Value("\${jwt.jwtExpiration}")
    private var daysValid: Long = 0

    override fun generateToken(userEntity: UserEntity): String {
        val date: Date = validBeforeToUtilDate(daysValid)

        return Jwts.builder()
            .setSubject(userEntity.name)
            .setExpiration(date)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
            return true
        } catch (e: Exception) {
            logger.error(e.message)
        }
        return false
    }

    fun getLoginFromToken(token: String): String {
        val claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token).body
        return claims.subject
    }
}