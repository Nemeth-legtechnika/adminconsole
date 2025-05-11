package com.nemethlegtechnika.orders.feature.usecase

import com.nemethlegtechnika.orders.domain.model.Owner

data class NotificationProduct (
    val name: String,
    val description: String,
    val price: Long,
    val quantity: Int,
) {
    val fullPrice: Long
        get() = price * quantity
}

data class NotificationOrder (
    val owner: Owner,
    val products: List<NotificationProduct>,
) {
    val totalPrice: Long
        get() = products.sumOf { it.fullPrice }
}

interface NotificationUseCase {
    suspend fun notifyNewOrder(order: NotificationOrder)
}