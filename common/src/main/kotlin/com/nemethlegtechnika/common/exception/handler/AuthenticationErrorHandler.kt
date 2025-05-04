package com.nemethlegtechnika.common.exception.handler

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component


@Component
class AuthenticationErrorHandler(
    private val mapper: ObjectMapper
): AuthenticationEntryPoint {

    override fun commence(request: HttpServletRequest?, response: HttpServletResponse, authException: AuthenticationException?) {
        val exception = ExceptionDto(
            "Unauthorized",
            HttpStatus.UNAUTHORIZED
        )
        val json = mapper.writeValueAsString(exception)

        response.status = exception.httpStatus.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(json)
        response.flushBuffer()
    }
}