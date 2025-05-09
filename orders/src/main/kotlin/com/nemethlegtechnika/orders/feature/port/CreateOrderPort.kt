package com.nemethlegtechnika.orders.feature.port

import com.nemethlegtechnika.orders.domain.command.ProductQuantity
import com.nemethlegtechnika.orders.domain.model.Order
import com.nemethlegtechnika.orders.domain.model.Owner

data class CreateOrder (
    val owner: Owner,
    val products: List<ProductQuantity>,
)

interface CreateOrderPort {
    suspend fun createOrder(order: CreateOrder): Order
}