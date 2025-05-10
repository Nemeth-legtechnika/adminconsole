package com.nemethlegtechnika.orders.feature.adapter

import com.nemethlegtechnika.orders.domain.repository.OrderRepository
import com.nemethlegtechnika.orders.feature.port.DeleteOrderPort
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class DeleteOrderAdapter(
    private val orderRepository: OrderRepository,
): DeleteOrderPort {
    override suspend fun deleteOrder(id: String) = withContext(Dispatchers.IO) {
        orderRepository.deleteById(id)
    }
}