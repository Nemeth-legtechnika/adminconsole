package com.nemethlegtechnika.orders.feature.client

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.PostExchange

data class AccessTokenResponse(
    @JsonProperty("access_token")
    val accessToken: String,

    @JsonProperty("expires_in")
    val expiresIn: Long,

    @JsonProperty("token_type")
    val tokenType: String,

    @JsonProperty("id_token")
    val idToken: String? = null,

    @JsonProperty("scope")
    val scope: String
)

interface TokenClient {

    @PostExchange(
        url = "/protocol/openid-connect/token",
        contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    fun getAccessToken(
        @RequestParam("grant_type") grantType: String,
        @RequestParam("client_id") clientId: String,
        @RequestParam("client_secret") clientSecret: String,
        @RequestParam("scope") scope: String = "openid roles",
    ): AccessTokenResponse
}