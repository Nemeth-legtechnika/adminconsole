package com.nemethlegtechnika.products.dto.exception

import com.nemethlegtechnika.products.mapper.Default
import org.springframework.http.HttpStatus
import java.io.Serializable

data class BindingErrorDto @Default constructor(
    val parameter: String,
    val message: String,
): Serializable

data class ValidationExceptionDto @Default constructor(
    val count: Int,
    val errors: List<BindingErrorDto>,
    val httpStatus: HttpStatus,
): Serializable