package com.nemethlegtechnika.orders.feature.port

import com.nemethlegtechnika.orders.domain.model.Order

interface UpdateOrderPort {
    suspend fun updateOrder(newOrder: Order): Order
}