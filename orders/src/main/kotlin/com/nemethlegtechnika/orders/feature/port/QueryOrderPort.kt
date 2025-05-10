package com.nemethlegtechnika.orders.feature.port

import com.nemethlegtechnika.orders.domain.model.Order

interface QueryOrderPort {
    suspend fun getAll(ids: List<String> = emptyList()): List<Order>

    suspend fun getAllByOwner(email: String): List<Order>

    suspend fun getAllInProgressOrder(ids: List<String> = emptyList()): List<Order>

    suspend fun getByIdAndOwner(id: String, email: String): Order

    suspend fun getById(id: String): Order

    suspend fun getInProgressById(id: String): Order
}