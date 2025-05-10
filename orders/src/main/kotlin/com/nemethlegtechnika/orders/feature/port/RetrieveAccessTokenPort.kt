package com.nemethlegtechnika.orders.feature.port

data class AccessToken(
    val accessToken: String,
    val expiresIn: Long,
)

val AccessToken.bearerToken: String
    get() = "Bearer $accessToken"

interface RetrieveAccessTokenPort {
    fun accessToken(): AccessToken
}