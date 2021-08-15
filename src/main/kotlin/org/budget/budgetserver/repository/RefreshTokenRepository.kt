package org.budget.budgetserver.repository

import org.budget.budgetserver.jpa.RefreshTokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.sql.Date


@Repository
interface RefreshTokenRepository : JpaRepository<RefreshTokenEntity, Int> {
    fun findByUserId(userId: Int): RefreshTokenEntity?

    fun findByUserIdAndToken(userId: Int, refreshToken: String): RefreshTokenEntity?

    fun deleteByUserId(userId: Int): Int

    fun deleteByUserIdAndToken(userId: Int, refreshToken: String): Int

    @Modifying
    @Query(
        """
        DELETE 
        FROM RefreshTokenEntity r 
        WHERE r.token = :token 
            AND r.userId = :userId            
            AND r.expireDate >= :now
            """
    )
    fun deleteValidToken(
        @Param("userId") userId: Int,
        @Param("token") token: String,
        @Param("now") now: Date
    ): Int
}