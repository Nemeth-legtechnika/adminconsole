package com.nemethlegtechnika.orders.feature.adapter

import com.nemethlegtechnika.common.exception.BackendException
import com.nemethlegtechnika.orders.feature.port.Email
import com.nemethlegtechnika.orders.feature.port.NotificationPort
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.mail.javamail.MimeMessagePreparator
import org.springframework.stereotype.Component

@Component
class NotificationAdapter(
    private val javaMailSender: JavaMailSender
): NotificationPort {
    override suspend fun sendEmail(email: Email) {
        MimeMessagePreparator { mimeMessage ->
            mimeMessage.let {
                MimeMessageHelper(it).apply {
                    setFrom("noreply@email.com")
                    setTo(email.email)
                    setSubject(email.subject)
                    setText(email.body, true)
                }
            }
        }.run {
            try {
                javaMailSender.send(this)
            } catch (e: MailException) {
                throw BackendException("Failed to send email to $email")
            }
        }
    }
}