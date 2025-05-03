package com.nemethlegtechnika.products.dto.user

data class UserDto(
    val username: String,
    val email: String,
    val roles: List<String>,
)
