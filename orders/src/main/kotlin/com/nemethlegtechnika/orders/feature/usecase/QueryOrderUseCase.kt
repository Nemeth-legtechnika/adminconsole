package com.nemethlegtechnika.orders.feature.usecase

import com.nemethlegtechnika.orders.domain.model.Order

interface QueryOrderUseCase {
    suspend fun getAll(): List<Order>

    suspend fun getById(id: String): Order
}