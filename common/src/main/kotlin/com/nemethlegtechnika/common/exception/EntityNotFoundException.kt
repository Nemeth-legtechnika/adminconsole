package com.nemethlegtechnika.common.exception

import kotlin.reflect.KClass

class EntityNotFoundException(
    message: String
) : BackendException(message){
    constructor(clazz: KClass<*>, attribute: String, value: Any): this("No ${clazz.simpleName} was found with $attribute: $value")

    companion object {
        inline fun <reified T> of(id: Long): BackendException {
            throw of<T>("id", id)
        }
        inline fun <reified T> of(attribute: String, value: Any): BackendException {
            throw EntityNotFoundException(T::class, attribute, value)
        }
    }
}