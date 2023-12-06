package com.nemethlegtechnika.products.util

import com.nemethlegtechnika.products.db.model.BaseEntity
import com.nemethlegtechnika.products.exception.BackendException
import com.nemethlegtechnika.products.exception.EntityNotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


inline fun <reified T: BaseEntity> Optional<T>.throwWhenNotFound(id: Long): T {
    return this.orElseThrow { EntityNotFoundException.of<T>(id) }
}

inline fun <reified T: BaseEntity> Optional<T>.throwWhenNotFound(attribute: String, value: String): T {
    return this.orElseThrow { EntityNotFoundException.of<T>(attribute, value) }
}

inline fun <reified T: BaseEntity> JpaRepository<T, Long>.findByIdOrThrow(id: Long): T {
    return this.findById(id).throwWhenNotFound(id)
}

inline fun <reified T: BaseEntity> JpaRepository<T, Long>.update(id: Long?, update: T.() -> Unit): T {
    id ?: throw BackendException("Id cannot be null for ${T::class.simpleName}")
    return this.findByIdOrThrow(id).let {
        it.update()
        this.saveAndFlush(it)
    }
}

val <T> Optional<T>.value: T? get() = this.orElse(null)