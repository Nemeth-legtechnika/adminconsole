package com.nemethlegtechnika.orders.domain.mapper

import com.nemethlegtechnika.orders.feature.client.AccessTokenResponse
import com.nemethlegtechnika.orders.feature.port.AccessToken

fun AccessTokenResponse.toAccessToken(): AccessToken {
    return AccessToken(
        accessToken = this.accessToken,
        expiresIn = this.expiresIn,
    )
}