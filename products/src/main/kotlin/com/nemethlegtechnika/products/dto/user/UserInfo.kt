package com.nemethlegtechnika.products.dto.user

data class UserInfo(
    val username: String,
    val email: String?,
    val roles: List<String>
)