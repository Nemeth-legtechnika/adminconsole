package com.nemethlegtechnika.products.service.response

import org.springframework.http.ResponseEntity

fun <T, R> single(mapper: (T) -> R, initializer: () -> T): Response<R> {
    return SingleResponse(mapper, initializer)
}

fun <T, R> list(mapper: (T) -> R, initializer: () -> List<T>): Response<List<R>> {
    return ListResponse(mapper, initializer)
}

fun <T, R> coList(mapper: (T) -> R, initializer: () -> List<T>): Response<List<R>> {
    return CoListResponse(mapper, initializer)
}

fun <T> Response<T>.resolve(resolver: ResponseResolver): ResponseEntity<T> {
    return resolver.resolve(this).let {
        ResponseEntity.ok(it)
    }
}