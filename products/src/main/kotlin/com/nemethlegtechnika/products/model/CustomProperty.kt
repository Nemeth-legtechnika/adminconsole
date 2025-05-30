package com.nemethlegtechnika.products.model

import com.nemethlegtechnika.common.exception.BackendException
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

enum class Type {
    STRING,
    DOUBLE,
    INT
}

@Entity
@Table(name = "custom_property")
class CustomProperty : BaseEntity() {

    @Column(name = "name", unique = true, nullable = false)
    var name: String? = null

    @Column(name = "value", nullable = false)
    var value: String = ""

    @Column(name = "type", nullable = false)
    var type: Type = Type.STRING

    final inline fun <reified T> value(): T {
        return try {
            when (T::class) {
                Double::class -> value.toDouble() as T
                Int::class -> value.toInt() as T
                String::class -> value as T
                else -> throw BackendException.typeNotSupported<CustomProperty>(T::class.simpleName)
            }
        } catch (e: NumberFormatException) {
            throw BackendException("Illegal value for ${T::class.simpleName}: $value")
        }
    }
}