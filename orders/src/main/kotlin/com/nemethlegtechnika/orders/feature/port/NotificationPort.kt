package com.nemethlegtechnika.orders.feature.port

data class Email(
    val email: String,
    val subject: String,
    val body: String
)

interface NotificationPort {
    suspend fun sendEmail(email: Email)
}