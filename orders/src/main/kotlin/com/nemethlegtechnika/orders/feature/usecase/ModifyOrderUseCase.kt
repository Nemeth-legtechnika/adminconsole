package com.nemethlegtechnika.orders.feature.usecase

import com.nemethlegtechnika.orders.domain.model.Order
import com.nemethlegtechnika.orders.feature.port.CreateOrder
import com.nemethlegtechnika.orders.feature.port.UpdateOrder

interface ModifyOrderUseCase {
    suspend fun createOrder(order: CreateOrder): Order
    suspend fun updateOrder(order: UpdateOrder): Order
    suspend fun deleteOrder(id: String)
}