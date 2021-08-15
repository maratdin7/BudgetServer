package org.budget.budgetserver.service.impl

import org.budget.budgetserver.jpa.GroupEntity
import org.budget.budgetserver.jpa.UserEntity
import org.budget.budgetserver.service.GroupService

class GroupServiceImpl : GroupService {
    override fun createGroup(groupName: String): Int {
        TODO("Not yet implemented")
    }

    override fun addUserToGroup(groupId: Int, login: String) {
        TODO("Not yet implemented")
    }

    override fun getAllGroup(): List<GroupEntity> {
        TODO("Not yet implemented")
    }

    override fun getAllUser(groupId: Int): List<UserEntity> {
        TODO("Not yet implemented")
    }
}