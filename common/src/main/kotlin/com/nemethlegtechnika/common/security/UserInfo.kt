package com.nemethlegtechnika.common.security

interface AbstractUserInfo {
    val username: String
    val roles: List<String>
}

data class UserInfo(
    override val username: String,
    val fullName: String,
    val email: String,
    override val roles: List<String>
): AbstractUserInfo

data class ServiceUserInfo(
    override val roles: List<String>
): AbstractUserInfo {
    override val username: String
        get() = "service-account"
}