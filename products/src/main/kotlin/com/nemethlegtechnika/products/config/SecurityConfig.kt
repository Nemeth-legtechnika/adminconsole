package com.nemethlegtechnika.products.config

import com.nemethlegtechnika.products.dto.user.UserInfo
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .authorizeHttpRequests { authorize ->
                authorize.anyRequest().hasRole("admin")
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt { jwt ->
                    jwt.jwtAuthenticationConverter(userInfoAuthenticationConverter())
                }
            }
            .build()
    }

    fun userInfoAuthenticationConverter(): Converter<Jwt, AbstractAuthenticationToken> {
        return Converter { jwt ->
            val email = jwt.getClaimAsString("email")
            val username = jwt.getClaimAsString("preferred_username")
            val roles = jwt.getClaimAsMap("realm_access")?.get("roles") as? List<String> ?: emptyList()

            val userInfo = UserInfo(
                username = username,
                email = email,
                roles = roles
            )

            UsernamePasswordAuthenticationToken(userInfo, jwt.tokenValue, roles.map { SimpleGrantedAuthority("ROLE_$it") })
        }
    }
}