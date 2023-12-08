package com.nemethlegtechnika.products.dto.exception

import com.nemethlegtechnika.products.mapper.Default
import org.springframework.http.HttpStatus
import java.io.Serializable
import java.util.*

data class ExceptionDto @Default constructor(
    val message: String,
    val httpStatus: HttpStatus,
    val timestamp: Date = Date()
): Serializable
