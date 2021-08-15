package org.budget.budgetserver.email

import org.budget.budgetserver.jpa.UserEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Component
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Component
@PropertySource("classpath:values.properties")
class EmailRepository {

    @Autowired
    private lateinit var env: Environment

    @Value("\${spring.mail.username}")
    private var sender: String = ""

    @Value("\${string.email.prefix}")
    private var prefix: String = ""

    fun completeRegistrationMessage(userEntity: UserEntity, token: String): SimpleMailMessage {
        with(env) {
            var url = getProperty("string.email.registrationConfirm.url", "Error")
            url = url.format(urlEncode(userEntity.name), token)

            var text = getProperty("string.email.registrationConfirm.text", "%s")
            text = text.format(url)

            val subject = getProperty("string.email.registrationConfirm.subject", "")

            return simpleMailMessage(userEntity.name, subject, text)
        }
    }

    private fun urlEncode(url: String) = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())

    fun resetPasswordMessage(userEntity: UserEntity, token: String): SimpleMailMessage {

        with(env) {

            val subject = getProperty("string.email.passwordRecovery.subject", "")

            var text = getProperty("string.email.passwordRecovery.text", "%s")
            text = text.format(token)

            return simpleMailMessage(userEntity.name, subject, text)
        }
    }

    private fun simpleMailMessage(email: String, subject: String, text: String): SimpleMailMessage =
        SimpleMailMessage().apply {
            setFrom(sender)
            setSubject(subject)
            setText(text)
            setTo(email)
        }

    private fun getProperty(postfix: String, defaultValue: String = "") =
        env.getProperty("$prefix.$postfix", defaultValue)

}