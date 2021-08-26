package org.budget.budgetserver.service.impl

import org.budget.budgetserver.event.OnInvitingUserToGroupEvent
import org.budget.budgetserver.exception.GroupAlreadyExistsException
import org.budget.budgetserver.exception.GroupNotFoundException
import org.budget.budgetserver.exception.UserIsAlreadyMemberOfGroupException
import org.budget.budgetserver.exception.UserIsNotMemberOfGroupException
import org.budget.budgetserver.jpa.GroupEntity
import org.budget.budgetserver.jpa.UserEntity
import org.budget.budgetserver.repository.GroupRepository
import org.budget.budgetserver.service.AccessService
import org.budget.budgetserver.service.GroupService
import org.budget.budgetserver.service.RoleInGroup
import org.budget.budgetserver.service.Service.getUserEntity
import org.budget.budgetserver.service.UserService
import org.budget.budgetserver.service.token.UserToGroupTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class GroupServiceImpl : GroupService {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var accessService: AccessService

    @Autowired
    private lateinit var userToGroupTokenService: UserToGroupTokenService

    @Autowired
    private lateinit var groupRepository: GroupRepository

    @Autowired
    private lateinit var eventPublisher: ApplicationEventPublisher

    override fun createGroup(groupName: String) {
        val userEntity = getUserEntity()
        if (accessService.isUserMemberOfGroupByName(userEntity.id, groupName))
            throw GroupAlreadyExistsException("Group $groupName already exists")

        val groupEntity = groupRepository.save(GroupEntity(name = groupName))

        accessService.createAccess(userEntity, groupEntity, RoleInGroup.ADMIN)
    }

    override fun invitationToJoinGroup(groupId: Int, emailForInvite: String) {
        val userEntity = getUserEntity()
        val accessEntity =
            accessService.findUserAccess(userEntity.id, groupId) ?: throw UserIsNotMemberOfGroupException()

        val invitedUserEntity = userService.findUserEntityByName(emailForInvite)
        accessService.userNotMemberOfGroup(invitedUserEntity.id, groupId)

        val groupEntity = accessEntity.refGroupEntity
        eventPublisher.publishEvent(OnInvitingUserToGroupEvent(invitedUserEntity, groupEntity))
    }

    override fun addUserToGroup(email: String, groupId: Int, token: String) {
        val userEntity = userService.findUserEntityByName(email)

        userToGroupTokenService.validateToken(userEntity.id, groupId, token)

        val groupEntity = groupRepository.findByIdOrNull(groupId) ?: throw GroupNotFoundException()

        accessService.createAccess(userEntity, groupEntity)
    }

    override fun getAllUserGroups(): List<GroupEntity> {
        val userEntity = getUserEntity()
        return accessService.findAllUserAccess(userEntity.id)
    }

    override fun getAllUsersInGroup(groupId: Int): List<UserEntity> = accessService.findAllUsersInGroup(groupId)
}