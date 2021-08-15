package org.budget.budgetserver.service.token

import org.budget.budgetserver.jpa.UserEntity
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.budget.budgetserver.service.token.DateConverter.validBeforeToUtilDate
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*


@Service
class JwtService {
    private val logger = LoggerFactory.getLogger(JwtService::class.java)

    @Value("\${jwt.secret}")
    private lateinit var jwtSecret: String

    @Value("\${jwt.jwtExpiration}")
    private var hourValid: Long = 0

    fun generateToken(userEntity: UserEntity): String {
        val date: Date = validBeforeToUtilDate(hourValid)

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