package org.budget.budgetserver.repository

import org.budget.budgetserver.jpa.AccessEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccessRepository : JpaRepository<AccessEntity, Int> {

    fun existsByUserIdAndGroupId(userId: Int, groupId: Int): Boolean

    fun findByGroupIdAndUserId(groupId: Int, userId: Int): List<AccessEntity>

    fun findByUserId(userId: Int): List<AccessEntity>

    fun findByGroupId(groupId: Int): List<AccessEntity>

}