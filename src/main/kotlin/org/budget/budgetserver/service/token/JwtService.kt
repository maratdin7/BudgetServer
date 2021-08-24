package org.budget.budgetserver.service.token

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.budget.budgetserver.jpa.UserEntity
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtService {
    private val logger = LoggerFactory.getLogger(JwtService::class.java)

    @Value("\${jwt.secret}")
    private lateinit var jwtSecret: String

    @Value("\${jwt.jwtExpirationInMillis}")
    private var jwtExpirationInMillis: Long = 0

    fun generateToken(userEntity: UserEntity): String {
        val nowInMillis = System.currentTimeMillis()
        val expInMillis = nowInMillis + jwtExpirationInMillis

        return Jwts.builder()
            .setSubject(userEntity.name)
            .setIssuedAt(Date(nowInMillis))
            .setExpiration(Date(expInMillis))
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