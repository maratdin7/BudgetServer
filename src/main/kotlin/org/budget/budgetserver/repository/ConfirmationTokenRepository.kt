package org.budget.budgetserver.repository

import org.budget.budgetserver.jpa.ConfirmationTokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ConfirmationTokenRepository : JpaRepository<ConfirmationTokenEntity, Int> {
    fun existsByUserId(userId: Int): Boolean

    fun findByUserId(userId: Int): ConfirmationTokenEntity?

    fun existsByUserIdAndToken(userId: Int, token: String): Boolean
}
