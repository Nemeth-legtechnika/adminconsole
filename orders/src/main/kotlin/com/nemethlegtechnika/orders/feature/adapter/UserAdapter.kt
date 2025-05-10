package com.nemethlegtechnika.orders.feature.adapter

import com.nemethlegtechnika.common.exception.EntityNotFoundException
import com.nemethlegtechnika.common.security.UserInfo
import com.nemethlegtechnika.orders.feature.port.UserPort
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserAdapter: UserPort {
    override fun getUser(): UserInfo =
        SecurityContextHolder.getContext().authentication?.let {
            (it.principal as? UserInfo)
        } ?: throw EntityNotFoundException("User not found in the security context")

    override fun isAdmin(): Boolean {
        return getUser().roles.any { it.lowercase() == "admin" }
    }
}