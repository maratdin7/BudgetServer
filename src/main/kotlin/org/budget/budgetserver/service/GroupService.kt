package org.budget.budgetserver.service

import org.budget.budgetserver.jpa.GroupEntity
import org.budget.budgetserver.jpa.UserEntity

interface GroupService {
    fun createGroup(groupName: String): Int

    fun addUserToGroup(groupId: Int, login: String)

    fun getAllGroup(): List<GroupEntity>

    fun getAllUser(groupId: Int): List<UserEntity>
}