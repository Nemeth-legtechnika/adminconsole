package com.nemethlegtechnika.products.dto.exception

import com.nemethlegtechnika.products.mapper.Default
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import java.io.Serializable
import java.util.*

data class ExceptionDto @Default constructor(
    val message: String,
    val httpStatus: HttpStatus,
    val timestamp: Date = Date(),
    val contentType: MediaType = MediaType.APPLICATION_JSON
): Serializable