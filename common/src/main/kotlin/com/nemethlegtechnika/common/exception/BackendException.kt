package com.nemethlegtechnika.common.exception

open class BackendException(
    message: String
): RuntimeException("[AdminConsole][Backend]: $message") {
    companion object {
        inline fun <reified T> typeNotSupported(type: String?): BackendException {
            throw BackendException("Type: ${type ?: "N/A"} is not supported for ${T::class.simpleName}")
        }
    }

    override val message: String get() = super.message!!
}