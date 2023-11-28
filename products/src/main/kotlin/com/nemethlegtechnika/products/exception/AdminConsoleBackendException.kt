package com.nemethlegtechnika.products.exception

import com.nemethlegtechnika.products.db.model.BaseEntity

class AdminConsoleBackendException(
    message: String
): Exception("[AdminConsole][Backend]: $message") {
    companion object {
        inline fun <reified T: BaseEntity> entityNotFound(id: Long): AdminConsoleBackendException {
            throw AdminConsoleBackendException("No ${T::class.simpleName} was found with id: $id")
        }
        inline fun <reified T: BaseEntity> entityNotFound(attribute: Any, value: String): AdminConsoleBackendException {
            throw AdminConsoleBackendException("No ${T::class.simpleName} was found with $attribute: $value")
        }
        inline fun <reified T: BaseEntity> typeNotSupported(type: String?): AdminConsoleBackendException {
            throw AdminConsoleBackendException("Type: ${type ?: "N/A"} is not supported for ${T::class.simpleName}")
        }
    }
}