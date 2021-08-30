package org.budget.budgetserver.service.impl

import org.budget.budgetserver.event.OnInvitingUserToGroupEvent
import org.budget.budgetserver.exception.GroupAlreadyExistsException
import org.budget.budgetserver.exception.GroupNotFoundException
import org.budget.budgetserver.jpa.GroupEntity
import org.budget.budgetserver.jpa.UserEntity
import org.budget.budgetserver.repository.GroupRepository
import org.budget.budgetserver.service.internal.AccessServiceInternal
import org.budget.budgetserver.service.GroupService
import org.budget.budgetserver.service.internal.RoleInGroup
import org.budget.budgetserver.service.internal.Service.getLoggedUserEntity
import org.budget.budgetserver.service.internal.UserServiceInternal
import org.budget.budgetserver.service.token.UserToGroupTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class GroupServiceImpl : GroupService {

    @Autowired
    private lateinit var userServiceInternal: UserServiceInternal

    @Autowired
    private lateinit var accessServiceInternal: AccessServiceInternal

    @Autowired
    private lateinit var userToGroupTokenService: UserToGroupTokenService

    @Autowired
    private lateinit var groupRepository: GroupRepository

    @Autowired
    private lateinit var eventPublisher: ApplicationEventPublisher

    override fun createGroup(groupName: String) {
        val userEntity = getLoggedUserEntity()
        if (accessServiceInternal.isUserMemberOfGroupByName(userEntity.id, groupName))
            throw GroupAlreadyExistsException("Group $groupName already exists")

        val groupEntity = groupRepository.save(GroupEntity(name = groupName))

        accessServiceInternal.createAccess(userEntity, groupEntity, RoleInGroup.ADMIN)
    }

    override fun invitationToJoinGroup(groupId: Int, emailForInvite: String) {
        val userEntity = getLoggedUserEntity()
        val accessEntity =
            accessServiceInternal.findUserAccess(userEntity.id, groupId)

        val invitedUserEntity = userServiceInternal.findUserEntityByName(emailForInvite)
        accessServiceInternal.userNotMemberOfGroup(invitedUserEntity.id, groupId)

        val groupEntity = accessEntity.refGroupEntity
        eventPublisher.publishEvent(OnInvitingUserToGroupEvent(invitedUserEntity, groupEntity))
    }

    override fun addUserToGroup(email: String, groupId: Int, token: String) {
        val userEntity = userServiceInternal.findUserEntityByName(email)

        userToGroupTokenService.validateToken(userEntity.id, groupId, token)

        val groupEntity = groupRepository.findByIdOrNull(groupId) ?: throw GroupNotFoundException()

        accessServiceInternal.createAccess(userEntity, groupEntity)
    }

    override fun getAllUserGroups(): List<GroupEntity> {
        val userEntity = getLoggedUserEntity()
        return accessServiceInternal.findAllUserAccess(userEntity.id)
    }

    override fun getAllUsersInGroup(groupId: Int): List<UserEntity> = accessServiceInternal.findAllUsersInGroup(groupId)
}