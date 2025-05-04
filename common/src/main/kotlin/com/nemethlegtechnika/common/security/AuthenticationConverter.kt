package com.nemethlegtechnika.common.security

import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt

fun userInfoAuthenticationConverter(): Converter<Jwt, AbstractAuthenticationToken> {
    return Converter { jwt ->
        val email = jwt.getClaimAsString("email")
        val username = jwt.getClaimAsString("preferred_username")
        val fullName = jwt.getClaimAsString("name")
        val roles = (jwt.getClaimAsMap("realm_access")?.get("roles") as? List<*>)?.filterIsInstance<String>() ?: emptyList()

        val userInfo = UserInfo(
            username = username,
            email = email,
            roles = roles,
            fullName = fullName,
        )

        UsernamePasswordAuthenticationToken(userInfo, jwt.tokenValue, roles.map { SimpleGrantedAuthority("ROLE_$it") })
    }
}