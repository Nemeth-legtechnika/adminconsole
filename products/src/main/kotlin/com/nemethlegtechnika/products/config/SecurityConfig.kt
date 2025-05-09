package com.nemethlegtechnika.products.config

import com.nemethlegtechnika.common.security.userInfoAuthenticationConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .authorizeHttpRequests { authorize ->
                authorize.anyRequest().hasAnyRole("admin", "service-account")
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt { jwt ->
                    jwt.jwtAuthenticationConverter(userInfoAuthenticationConverter())
                }
            }
            .build()
    }
}