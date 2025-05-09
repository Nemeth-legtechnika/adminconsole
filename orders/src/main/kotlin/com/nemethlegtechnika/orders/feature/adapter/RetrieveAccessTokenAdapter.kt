package com.nemethlegtechnika.orders.feature.adapter

import com.nemethlegtechnika.orders.domain.mapper.toAccessToken
import com.nemethlegtechnika.orders.feature.client.TokenClient
import com.nemethlegtechnika.orders.feature.port.AccessToken
import com.nemethlegtechnika.orders.feature.port.RetrieveAccessTokenPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class RetrieveAccessTokenAdapter(
    private val tokenClient: TokenClient,
): RetrieveAccessTokenPort {
    @Value("\${service.keycloak.client-id}")
    private lateinit var clientId: String

    @Value("\${service.keycloak.client-secret}")
    private lateinit var clientSecret: String

    override fun accessToken(): AccessToken = tokenClient.getAccessToken(
        grantType = "client_credentials",
        clientId = clientId,
        clientSecret = clientSecret,
    ).toAccessToken()
}