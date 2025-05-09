package com.nemethlegtechnika.orders.feature.adapter

import com.nemethlegtechnika.common.exception.EntityNotFoundException
import com.nemethlegtechnika.orders.domain.model.Order
import com.nemethlegtechnika.orders.domain.repository.OrderRepository
import com.nemethlegtechnika.orders.feature.port.QueryOrderPort
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class QueryOrderAdapter(
    private val orderRepository: OrderRepository,
): QueryOrderPort {
    override suspend fun getAll(): List<Order> = orderRepository.findAll()

    override suspend fun getAllByOwner(email: String): List<Order> = withContext(Dispatchers.IO) {
        orderRepository.findByOwnerEmail(email)
    }

    override suspend fun getByIdAndOwner(id: String, email: String): Order = withContext(Dispatchers.IO) {
        orderRepository.findByIdAndOwnerEmail(id, email) ?: throw EntityNotFoundException("Order with id: $id not found for owner with email: $email")
    }

    override suspend fun getById(id: String): Order = withContext(Dispatchers.IO) {
        orderRepository.findById(id).orElseThrow{ EntityNotFoundException("Order with id: $id not found") }
    }
}