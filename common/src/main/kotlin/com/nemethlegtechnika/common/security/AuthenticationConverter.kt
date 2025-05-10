package com.nemethlegtechnika.common.security

import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt

fun userInfoAuthenticationConverter(): Converter<Jwt, AbstractAuthenticationToken> {
    return Converter { jwt ->
        val roles = (jwt.getClaimAsMap("realm_access")?.get("roles") as? List<*>)?.filterIsInstance<String>() ?: emptyList()

        val userInfo = ifServiceAccount(roles) {
            ServiceUserInfo(
                roles = roles,
            )
        } ?: UserInfo(
            username = jwt.getClaimAsString("preferred_username"),
            email = jwt.getClaimAsString("email"),
            roles = roles,
            fullName = jwt.getClaimAsString("name"),
        )

        UsernamePasswordAuthenticationToken(userInfo, jwt.tokenValue, roles.map { SimpleGrantedAuthority("ROLE_$it") })
    }
}

private fun isServiceAccount(roles: List<String>): Boolean = roles.any { it == "service-account" }

private fun <T> ifServiceAccount(roles: List<String>, block: () -> T): T? {
    if (isServiceAccount(roles)) {
        return block()
    }
    return null
}