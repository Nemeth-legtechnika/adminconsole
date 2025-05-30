package com.nemethlegtechnika.orders.feature.adapter

import com.nemethlegtechnika.common.exception.EntityNotFoundException
import com.nemethlegtechnika.orders.domain.model.Order
import com.nemethlegtechnika.orders.domain.repository.OrderRepository
import com.nemethlegtechnika.orders.feature.port.UpdateOrderPort
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.getOrNull

@Component
class UpdateOrderAdapter(
    private val orderRepository: OrderRepository,
): UpdateOrderPort {
    override suspend fun updateOrder(newOrder: Order): Order = withContext(Dispatchers.IO) {
        orderRepository.findById(newOrder.id!!).getOrNull()?.copy(
            products = newOrder.products,
            owner = newOrder.owner,
            status = newOrder.status,
        )?.also {
            orderRepository.save(it)
        } ?: throw EntityNotFoundException("Order with id: ${newOrder.id} not found")
    }
}