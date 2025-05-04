package com.nemethlegtechnika.orders.feature.adapter

import com.nemethlegtechnika.orders.domain.repository.OrderRepository
import org.springframework.stereotype.Component

@Component
class CreateOrderPort(
    private val orderRepository: OrderRepository,
    private val productClient: ProductClient,
) {
}