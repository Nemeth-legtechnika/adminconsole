package com.nemethlegtechnika.products.util

import com.nemethlegtechnika.products.dto.exception.ExceptionDto
import org.springframework.http.ResponseEntity


inline fun Long.round(action: (Long) -> Double): Long {
    val value = action(this)
    return kotlin.math.round(value).toLong()
}

val ExceptionDto.response: ResponseEntity<ExceptionDto>
    get() = ResponseEntity.status(this.httpStatus).body(this)
