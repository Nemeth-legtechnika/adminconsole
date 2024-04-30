package com.nemethlegtechnika.products.config

import org.springframework.context.annotation.Bean
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.web.SecurityFilterChain


@EnableWebSecurity
class SecurityConfig(
    private val authenticationErrorHandler: AuthenticationErrorHandler,
) {


    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain = http
        .authorizeHttpRequests {
            it.requestMatchers("/api/**").hasRole("Admin")
        }
        .cors(Customizer.withDefaults())
        .oauth2ResourceServer {
            it.jwt { jwt -> jwt.jwtAuthenticationConverter(makePermissionsConverter()) }
                .authenticationEntryPoint(authenticationErrorHandler)
        }
        .build()

    private fun makePermissionsConverter(): JwtAuthenticationConverter {
        val jwtAuthoritiesConverter = JwtGrantedAuthoritiesConverter()
        jwtAuthoritiesConverter.setAuthoritiesClaimName("permissions")
        jwtAuthoritiesConverter.setAuthorityPrefix("")

        val jwtAuthConverter = JwtAuthenticationConverter()
        jwtAuthConverter.setJwtGrantedAuthoritiesConverter(jwtAuthoritiesConverter)

        return jwtAuthConverter
    }
}