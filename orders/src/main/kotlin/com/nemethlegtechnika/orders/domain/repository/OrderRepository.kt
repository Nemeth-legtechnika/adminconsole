package com.nemethlegtechnika.orders.domain.repository

import com.nemethlegtechnika.orders.domain.model.Order
import org.springframework.data.mongodb.repository.MongoRepository

interface OrderRepository: MongoRepository<Order, String> {
    fun findByProductsId(productId: Long): List<Order>
    fun findByOwnerEmail(email: String): List<Order>
    fun findByIdAndOwnerEmail(id: String, email: String): Order?
}