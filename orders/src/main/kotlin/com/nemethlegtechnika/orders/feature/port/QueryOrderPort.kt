package com.nemethlegtechnika.orders.feature.port

import com.nemethlegtechnika.orders.domain.model.Order

interface QueryOrderPort {
    suspend fun getAll(): List<Order>

    suspend fun getAllByOwner(email: String): List<Order>

    suspend fun getByIdAndOwner(id: String, email: String): Order
}