package com.nemethlegtechnika.products.controller

import com.nemethlegtechnika.products.dto.exception.BindingErrorDto
import com.nemethlegtechnika.products.dto.exception.ExceptionDto
import com.nemethlegtechnika.products.dto.exception.ValidationExceptionDto
import com.nemethlegtechnika.products.exception.BackendException
import com.nemethlegtechnika.products.exception.ConstraintViolationException
import com.nemethlegtechnika.products.exception.EntityNotFoundException
import com.nemethlegtechnika.products.util.response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(EntityNotFoundException::class)
    fun notFound(exception: EntityNotFoundException): ResponseEntity<ExceptionDto> {
        val exceptionDto = ExceptionDto(
            exception.message,
            HttpStatus.NOT_FOUND,
        )
        return exceptionDto.response
    }

    @ExceptionHandler(BackendException::class)
    fun commonBadRequest(exception: BackendException): ResponseEntity<ExceptionDto> {
        val exceptionDto = ExceptionDto(
            exception.message,
            HttpStatus.BAD_REQUEST,
        )
        return exceptionDto.response
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun validationError(exception: MethodArgumentNotValidException): ResponseEntity<ValidationExceptionDto> {
        val exceptionDto = ValidationExceptionDto(
            exception.bindingResult.errorCount,
            exception.bindingResult.allErrors.map { error ->
                BindingErrorDto(
                    (error as FieldError).field,
                    error.defaultMessage ?: "Unknown error",
                )
            },
            HttpStatus.BAD_REQUEST,
        )
        return exceptionDto.response
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun constraintViolation(exception: ConstraintViolationException): ResponseEntity<ExceptionDto> {
        val exceptionDto = ExceptionDto(
            exception.constraintMessage,
            HttpStatus.BAD_REQUEST,
        )
        return exceptionDto.response
    }
}