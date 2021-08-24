package org.budget.budgetserver.event

import org.budget.budgetserver.jpa.GroupEntity
import org.budget.budgetserver.jpa.UserEntity
import org.springframework.context.ApplicationEvent

class OnInvitingUserToGroupEvent(userEntity: UserEntity, groupEntity: GroupEntity) :
    AbstractOnMailSendingEvent<Pair<UserEntity, GroupEntity>>(Pair(userEntity, groupEntity))

class OnRegistrationCompleteEvent(userEntity: UserEntity) : AbstractOnMailSendingEvent<UserEntity>(userEntity)

class OnResetPasswordEvent(userEntity: UserEntity) : AbstractOnMailSendingEvent<UserEntity>(userEntity)

abstract class AbstractOnMailSendingEvent<T : Any>(val s: T) : ApplicationEvent(s)