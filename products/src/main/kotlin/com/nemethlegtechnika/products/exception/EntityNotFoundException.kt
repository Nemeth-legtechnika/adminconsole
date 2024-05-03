package com.nemethlegtechnika.products.exception

import com.nemethlegtechnika.products.model.BaseEntity
import kotlin.reflect.KClass

class EntityNotFoundException(
    message: String
) : BackendException(message){
    constructor(clazz: KClass<*>, attribute: String, value: Any): this("No ${clazz.simpleName} was found with $attribute: $value")

    companion object {
        inline fun <reified T: BaseEntity> of(id: Long): BackendException {
            throw of<T>("id", id)
        }
        inline fun <reified T: BaseEntity> of(attribute: String, value: Any): BackendException {
            throw EntityNotFoundException(T::class, attribute, value)
        }
    }
}