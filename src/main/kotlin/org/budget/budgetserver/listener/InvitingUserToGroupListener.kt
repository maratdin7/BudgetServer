package org.budget.budgetserver.listener

import org.budget.budgetserver.event.OnInvitingUserToGroupEvent
import org.budget.budgetserver.jpa.GroupEntity
import org.budget.budgetserver.jpa.UserEntity
import org.budget.budgetserver.service.token.UserToGroupTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Component

@Component
class InvitingUserToGroupListener :
    AbstractMailEventListener<OnInvitingUserToGroupEvent, Pair<UserEntity, GroupEntity>>() {

    @Autowired
    private lateinit var userToGroupTokenService: UserToGroupTokenService

    override fun getMessage(source: Pair<UserEntity, GroupEntity>): SimpleMailMessage {
        val (userEntity, groupEntity) = source

        val token = userToGroupTokenService.generateToken(userEntity, groupEntity)

        return emailRepository.invitingUserToGroupMessage(userEntity, groupEntity, token)
    }
}