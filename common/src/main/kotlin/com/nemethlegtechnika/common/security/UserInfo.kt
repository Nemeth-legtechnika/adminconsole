package com.nemethlegtechnika.common.security

data class UserInfo(
    val username: String,
    val fullName: String,
    val email: String?,
    val roles: List<String>
)