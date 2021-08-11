package org.budget.budgetserver.repository

import org.budget.budgetserver.jpa.RefreshTokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface RefreshTokenRepository : JpaRepository<RefreshTokenEntity, Int> {
    fun findByUserId(userId: Int): RefreshTokenEntity?

    fun findByToken(refreshToken: String): RefreshTokenEntity?

    fun deleteByUserId(userId: Int): Int
}