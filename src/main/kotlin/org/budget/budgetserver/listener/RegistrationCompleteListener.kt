package org.budget.budgetserver.listener

import org.budget.budgetserver.event.OnRegistrationCompleteEvent
import org.budget.budgetserver.jpa.UserEntity
import org.budget.budgetserver.service.token.ConfirmationTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Component

@Component
class RegistrationCompleteListener : AbstractMailEventListener<OnRegistrationCompleteEvent, UserEntity>() {

    @Autowired
    private lateinit var confirmationTokenService: ConfirmationTokenService

    override fun getMessage(source: UserEntity): SimpleMailMessage {
        val token = confirmationTokenService.generateToken(source)

        return emailRepository.completeRegistrationMessage(source.name, token)
    }
}