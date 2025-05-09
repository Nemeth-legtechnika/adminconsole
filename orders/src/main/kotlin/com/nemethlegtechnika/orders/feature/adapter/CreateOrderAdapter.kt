package com.nemethlegtechnika.orders.feature.adapter

import com.nemethlegtechnika.orders.domain.model.Order
import com.nemethlegtechnika.orders.domain.repository.OrderRepository
import com.nemethlegtechnika.orders.feature.port.CreateOrder
import com.nemethlegtechnika.orders.feature.port.CreateOrderPort
import com.nemethlegtechnika.orders.feature.port.RequestProductPort
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class CreateOrderAdapter(
    private val orderRepository: OrderRepository,
    private val requestProductPort: RequestProductPort
): CreateOrderPort {
    override suspend fun createOrder(order: CreateOrder): Order = withContext(Dispatchers.IO) {
        Order(
            owner = order.owner,
            products = requestProductPort.getProducts(order.products).toMutableList()
        ).let { order ->
            orderRepository.save(order)
        }
    }
}