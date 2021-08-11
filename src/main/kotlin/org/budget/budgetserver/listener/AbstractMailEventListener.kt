package org.budget.budgetserver.listener

import org.budget.budgetserver.email.EmailRepository
import org.budget.budgetserver.event.AbstractOnMailSendingEvent
import org.budget.budgetserver.jpa.UserEntity
import org.budget.budgetserver.service.token.AbstractTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.mail.MailException
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender

abstract class AbstractMailEventListener<T : AbstractOnMailSendingEvent> : ApplicationListener<T> {
    @Autowired
    private lateinit var mailSender: JavaMailSender

    @Autowired
    protected lateinit var emailRepository: EmailRepository

    protected abstract fun getMessage(userEntity: UserEntity): SimpleMailMessage

    override fun onApplicationEvent(event: T) {
        val message = getMessage(event.userEntity)
        sendMail(message)
    }

    private fun sendMail(message: SimpleMailMessage) {
        try {
            mailSender.send(message)
        } catch (e: MailException) {
            // замени на нормальный лог
            println(e.message)
        }
    }



}