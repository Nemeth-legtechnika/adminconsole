package com.nemethlegtechnika.products.service.response

import org.springframework.http.ResponseEntity

enum class Mode { READ, WRITE }

fun <T, R> single(mapper: (T) -> R, initializer: () -> T): Response<R> {
    return SingleResponse(mapper, initializer)
}

fun <T, R> list(mapper: (T) -> R, initializer: () -> List<T>): Response<List<R>> {
    return ListResponse(mapper, initializer)
}

fun <T, R> coList(mapper: (T) -> R, initializer: () -> List<T>): Response<List<R>> {
    return CoListResponse(mapper, initializer)
}

fun <T> Response<T>.resolve(resolver: ResponseResolver, mode: Mode = Mode.READ): ResponseEntity<T> {
    return when(mode) {
        Mode.READ -> resolver.read(this)
        Mode.WRITE -> resolver.write(this)
    }.let {
        ResponseEntity.ok(it)
    }
}

infix fun <T> Response<T>.read(resolver: ResponseResolver): ResponseEntity<T> {
    return resolve(resolver)
}

infix fun <T> Response<T>.write(resolver: ResponseResolver): ResponseEntity<T> {
    return resolve(resolver, Mode.WRITE)
}
