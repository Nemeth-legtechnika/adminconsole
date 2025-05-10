package com.nemethlegtechnika.common.exception.handler

import org.springframework.http.HttpStatus
import java.io.Serializable

data class BindingErrorDto (
    val parameter: String,
    val message: String,
): Serializable

data class ValidationExceptionDto (
    val count: Int,
    val errors: List<BindingErrorDto>,
    val httpStatus: HttpStatus,
): Serializable