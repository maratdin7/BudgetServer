package org.budget.budgetserver.repository

import org.budget.budgetserver.jpa.ResetPasswordTokenEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ResetPasswordTokenRepository : JpaRepository<ResetPasswordTokenEntity, Int> {
    fun findByUserIdAndToken(userId: Int, token: Int): ResetPasswordTokenEntity?

    fun findByUserId(userId: Int): ResetPasswordTokenEntity?
}