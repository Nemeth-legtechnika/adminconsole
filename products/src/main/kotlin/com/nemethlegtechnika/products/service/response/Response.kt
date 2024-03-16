package com.nemethlegtechnika.products.service.response

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking

interface Response<T> {
    val value: T
}

sealed class ServiceResponse<T, R>(
    val mapper: (T) -> R,
    val initializer: () -> T,
): Response<R> {

    override val value: R
        get() = initializer().map()

    protected abstract fun T.map(): R
}

class SingleResponse<T, R>(
    mapper: (T) -> R,
    initializer: () -> T,
): ServiceResponse<T, R>(mapper, initializer) {
    override fun T.map(): R = mapper(this)
}

class ListResponse<T, R>(
    private val singleMapper: (T) -> R,
    initializer: () -> List<T>,
): ServiceResponse<List<T>, List<R>>({ list -> list.map { singleMapper(it) } }, initializer) {

    override fun List<T>.map(): List<R> = mapper(this)
}

private fun <T, R> List<T>.mapAsync(mapper: (T) -> R): List<R> = runBlocking {
    this@mapAsync.asFlow().map { mapper(it) }.toList()
}

class CoListResponse<T, R>(
    private val singleMapper: (T) -> R,
    initializer: () -> List<T>,
): ServiceResponse<List<T>, List<R>>({list -> list.mapAsync { singleMapper(it) }}, initializer) {

    override fun List<T>.map(): List<R> = mapper(this)
}