package org.budget.budgetserver.listener

import org.budget.budgetserver.email.EmailRepository
import org.budget.budgetserver.event.OnRegistrationCompleteEvent
import org.budget.budgetserver.event.OnResetPasswordEvent
import org.budget.budgetserver.jpa.UserEntity
import org.budget.budgetserver.service.token.ConfirmationTokenService
import org.budget.budgetserver.service.token.ResetPasswordTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.PropertySource
import org.springframework.mail.MailException
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class ResetPasswordListener : AbstractMailEventListener<OnResetPasswordEvent>() {
    @Autowired
    private lateinit var resetPasswordTokenService: ResetPasswordTokenService

    override fun getMessage(userEntity: UserEntity): SimpleMailMessage {
        val token = resetPasswordTokenService.generateToken(userEntity)

        return emailRepository.ResetPasswordMessage(userEntity, token)
    }
}