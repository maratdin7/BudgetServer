package org.budget.budgetserver.event

import org.budget.budgetserver.jpa.UserEntity
import org.springframework.context.ApplicationEvent

class OnRegistrationCompleteEvent(userEntity: UserEntity) : AbstractOnMailSendingEvent(userEntity)

class OnResetPasswordEvent(userEntity: UserEntity) : AbstractOnMailSendingEvent(userEntity)

abstract class AbstractOnMailSendingEvent(val userEntity: UserEntity) : ApplicationEvent(userEntity)