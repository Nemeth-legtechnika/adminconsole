package com.nemethlegtechnika.orders.feature.usecase

import com.nemethlegtechnika.orders.domain.command.ProductQuantity
import com.nemethlegtechnika.orders.domain.model.Order
import com.nemethlegtechnika.orders.domain.model.OrderStatus
import com.nemethlegtechnika.orders.domain.model.Owner

data class UpdateOrder(
    val id: String,
    val products: List<ProductQuantity>,
    val owner: Owner,
    val status: OrderStatus,
)

data class CreateOrder (
    val owner: Owner,
    val products: List<ProductQuantity>,
)

interface ModifyOrderUseCase {
    suspend fun createOrder(order: CreateOrder): Order
    suspend fun updateOrder(order: UpdateOrder): Order
    suspend fun deleteOrder(id: String)
}