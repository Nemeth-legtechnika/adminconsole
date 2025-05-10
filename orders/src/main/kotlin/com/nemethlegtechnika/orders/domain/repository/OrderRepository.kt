package com.nemethlegtechnika.orders.domain.repository

import com.nemethlegtechnika.orders.domain.model.Order
import com.nemethlegtechnika.orders.domain.model.OrderStatus
import org.springframework.data.mongodb.repository.MongoRepository

interface OrderRepository: MongoRepository<Order, String> {
    fun findAllByIdIn(ids: List<String>): List<Order>
    fun findAllByOwnerEmail(email: String): List<Order>
    fun findAllByStatus(status: OrderStatus): List<Order>
    fun findAllByIdInAndStatus(ids: List<String>, status: OrderStatus): List<Order>
    fun findByIdAndOwnerEmail(id: String, email: String): Order?
    fun findByIdAndStatus(id: String, status: OrderStatus): Order?
}