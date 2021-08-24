package org.budget.budgetserver.listener

import org.budget.budgetserver.event.OnResetPasswordEvent
import org.budget.budgetserver.jpa.UserEntity
import org.budget.budgetserver.service.token.ResetPasswordTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Component

@Component
class ResetPasswordListener : AbstractMailEventListener<OnResetPasswordEvent, UserEntity>() {
    @Autowired
    private lateinit var resetPasswordTokenService: ResetPasswordTokenService

    override fun getMessage(source: UserEntity): SimpleMailMessage {
        val token = resetPasswordTokenService.generateToken(source)

        return emailRepository.resetPasswordMessage(source.name, token.toString())
    }
}