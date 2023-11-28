package com.nemethlegtechnika.products.util

import com.nemethlegtechnika.products.db.model.BaseEntity
import com.nemethlegtechnika.products.exception.AdminConsoleBackendException
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


inline fun Long.round(action: (Long) -> Double): Long {
    val value = action(this)
    return kotlin.math.round(value).toLong()
}

inline fun <reified T: BaseEntity> Optional<T>.throwWhenNotFound(id: Long): T {
    return this.orElseThrow { AdminConsoleBackendException.entityNotFound<T>(id) }
}
inline fun <reified T: BaseEntity> JpaRepository<T, Long>.findByIdOrThrow(id: Long): T {
    return this.findById(id).throwWhenNotFound(id)
}
inline fun <reified T: BaseEntity> JpaRepository<T, Long>.update(id: Long, update: T.() -> Unit): T {
    val entity = this.findByIdOrThrow(id)
    entity.update()
    return this.saveAndFlush(entity)
}