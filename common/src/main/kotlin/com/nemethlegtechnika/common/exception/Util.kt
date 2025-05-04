package com.nemethlegtechnika.common.exception

import com.nemethlegtechnika.common.exception.handler.ExceptionDto
import com.nemethlegtechnika.common.exception.handler.ValidationExceptionDto
import org.springframework.http.ResponseEntity

val ExceptionDto.response: ResponseEntity<ExceptionDto>
    get() = ResponseEntity.status(this.httpStatus).body(this)

val ValidationExceptionDto.response: ResponseEntity<ValidationExceptionDto>
    get() = ResponseEntity.status(this.httpStatus).body(this)