package com.nemethlegtechnika.orders.feature.port

import com.nemethlegtechnika.orders.domain.dto.OrderDto

interface UpdateOrderPort {
    fun updateOrder(order: OrderDto): OrderDto
}