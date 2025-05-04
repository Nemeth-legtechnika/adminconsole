package com.nemethlegtechnika.orders.feature.port

import com.nemethlegtechnika.orders.domain.dto.OrderDto

interface CreateOrderPort {
    fun createOrder(order: OrderDto): OrderDto
}