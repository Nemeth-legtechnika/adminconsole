package com.nemethlegtechnika.orders.util

inline fun <reified T : Enum<T>> valueOfOrThrow(value: String, throwable: (String) -> Nothing): T {
    return value.let {
        runCatching { enumValueOf<T>(it) }.getOrElse {
            throwable(value)
        }
    }
}