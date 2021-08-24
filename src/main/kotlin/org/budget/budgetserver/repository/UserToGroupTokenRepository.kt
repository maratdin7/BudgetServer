package org.budget.budgetserver.repository

import org.budget.budgetserver.jpa.UserToGroupTokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserToGroupTokenRepository : JpaRepository<UserToGroupTokenEntity, Int> {

    fun findByUserIdAndGroupId(userId: Int, groupId: Int): UserToGroupTokenEntity?

    fun findByUserIdAndGroupIdAndToken(userId: Int, groupId: Int, token: String): UserToGroupTokenEntity?

}