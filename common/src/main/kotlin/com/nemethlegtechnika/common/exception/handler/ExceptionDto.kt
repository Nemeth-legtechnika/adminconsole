package com.nemethlegtechnika.common.exception.handler

import org.springframework.http.HttpStatus
import java.io.Serializable
import java.util.*

data class ExceptionDto constructor(
    val message: String,
    val httpStatus: HttpStatus,
    val timestamp: Date = Date(),
): Serializable