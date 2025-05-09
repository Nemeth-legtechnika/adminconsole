package com.nemethlegtechnika.orders.feature.port

import com.nemethlegtechnika.common.security.UserInfo

interface UserPort {
    fun getUser(): UserInfo

    fun isAdmin(): Boolean
}