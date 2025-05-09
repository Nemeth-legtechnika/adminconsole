package com.nemethlegtechnika.orders.feature.port

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

interface UpdateOrderPort {
    suspend fun updateOrder(newOrder: UpdateOrder): Order
}