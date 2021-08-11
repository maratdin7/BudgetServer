package org.budget.budgetserver.template

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Component

//@Component
//object EmailTemplate {
//
//    @Autowired
//    private lateinit var env: Environment
//
//    @Value("\${spring.mail.username}")
//    private var sender: String = ""
//
//    val completeRegistration = SimpleMailMessage().apply {
//        setFrom(sender)
//        setSubject(env.getProperty("string.email.registrationConfirm.subject", ""))
//        setText(env.getProperty("string.email.registrationConfirm.text", "%s"))
//    }
//
//    val passwordRecovery = SimpleMailMessage().apply {
//        setFrom(sender)
//        setSubject(env.getProperty("string.email.passwordRecovery.subject", ""))
//        setText(env.getProperty("string.email.passwordRecovery.text", "%d"))
//    }
//
//    val addToGroup = SimpleMailMessage().apply {
//        setFrom(sender)
//        setSubject(env.getProperty("string.email.addToGroup.subject", ""))
//        setText(env.getProperty("string.email.addToGroup.text", ""))
//    }
//}