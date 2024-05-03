package com.nemethlegtechnika.products.exception

class ConstraintViolationException(
    message: String
) : BackendException(message) {
    val constraintMessage = message
}