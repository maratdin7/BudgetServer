package org.budget.budgetserver.service.internal

import org.budget.budgetserver.exception.UserIsAlreadyMemberOfGroupException
import org.budget.budgetserver.exception.UserIsNotMemberOfGroupException
import org.budget.budgetserver.jpa.AccessEntity
import org.budget.budgetserver.jpa.GroupEntity
import org.budget.budgetserver.jpa.UserEntity
import org.budget.budgetserver.repository.AccessRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AccessServiceInternal {

    @Autowired
    private lateinit var accessRepository: AccessRepository

    fun createAccess(
        userEntity: UserEntity,
        groupEntity: GroupEntity,
        role: RoleInGroup = RoleInGroup.USER
    ): AccessEntity = accessRepository.save(
        AccessEntity(
            refUserEntity = userEntity,
            refGroupEntity = groupEntity,
            role = role
        )
    )

    fun userMemberOfGroup(userId: Int, groupId: Int): Boolean =
        if (isUserMemberOfGroup(userId, groupId))
            true
        else throw UserIsNotMemberOfGroupException()

    fun userNotMemberOfGroup(userId: Int, groupId: Int): Boolean =
        if (isUserMemberOfGroup(userId, groupId))
            throw UserIsAlreadyMemberOfGroupException()
        else true

    fun isUserMemberOfGroup(userId: Int, groupId: Int): Boolean =
        accessRepository.existsByUserIdAndGroupId(userId, groupId)

    fun findUserAccess(userId: Int, groupId: Int): AccessEntity =
        accessRepository.findByUserIdAndGroupId(userId, groupId) ?: throw UserIsNotMemberOfGroupException()

    fun findAllUserAccess(userId: Int): List<GroupEntity> = accessRepository.findAllUserGroups(userId)

    fun findAllUsersInGroup(groupId: Int): List<UserEntity> = accessRepository.findAllUsersInGroup(groupId)

    fun isUserMemberOfGroupByName(userId: Int, groupName: String): Boolean =
        accessRepository.isUserMemberOfGroupByName(userId, groupName).isNotEmpty()
}

enum class RoleInGroup {
    ADMIN,
    USER
}