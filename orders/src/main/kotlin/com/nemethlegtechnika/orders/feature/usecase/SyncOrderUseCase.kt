package com.nemethlegtechnika.orders.feature.usecase

import com.nemethlegtechnika.orders.domain.model.Order

interface SyncOrderUseCase {
    suspend fun syncAll(ids: List<String> = emptyList()): List<Order>
    suspend fun syncById(id: String): Order
}