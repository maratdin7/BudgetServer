package org.budget.budgetserver.service

import org.budget.budgetserver.jpa.GroupEntity
import org.budget.budgetserver.jpa.UserEntity

interface GroupService {
    fun createGroup(groupName: String)

    fun getAllUserGroups(): List<GroupEntity>

    fun getAllUsersInGroup(groupId: Int): List<UserEntity>

    fun addUserToGroup(email: String, groupId: Int, token: String)

    fun invitationToJoinGroup(groupId: Int, emailForInvite: String)
}