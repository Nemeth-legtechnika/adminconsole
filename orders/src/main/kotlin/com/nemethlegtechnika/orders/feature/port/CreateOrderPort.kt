package com.nemethlegtechnika.orders.feature.port

import com.nemethlegtechnika.orders.domain.model.Order

interface CreateOrderPort {
    suspend fun createOrder(order: Order): Order
}