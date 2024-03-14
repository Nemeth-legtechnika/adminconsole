package com.nemethlegtechnika.products.db.model

import com.nemethlegtechnika.products.exception.BackendException
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
        return when(T::class) {
            Double::class -> value.toDouble() as T
            Int::class -> value.toInt() as T
            String::class -> value as T
            else -> throw BackendException.typeNotSupported<CustomProperty>(T::class.simpleName)
        }
    }
}