package com.nemethlegtechnika.orders.feature.port

interface DeleteOrderPort {
    suspend fun deleteOrder(id: String)
}