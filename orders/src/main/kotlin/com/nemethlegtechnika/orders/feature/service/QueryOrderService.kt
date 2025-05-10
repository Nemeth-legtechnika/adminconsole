package com.nemethlegtechnika.orders.feature.service

import com.nemethlegtechnika.orders.domain.model.Order
import com.nemethlegtechnika.orders.feature.port.QueryOrderPort
import com.nemethlegtechnika.orders.feature.port.UserPort
import com.nemethlegtechnika.orders.feature.usecase.QueryOrderUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class QueryOrderService(
    private val queryOrderPort: QueryOrderPort,
    private val userPort: UserPort,
): QueryOrderUseCase {
    override suspend fun getAll(): List<Order> = withContext(Dispatchers.IO) {
        if (userPort.isAdmin()) {
            queryOrderPort.getAll()
        } else {
            queryOrderPort.getAllByOwner(userPort.getUser().email)
        }
    }

    override suspend fun getById(id: String): Order = withContext(Dispatchers.IO) {
        if (userPort.isAdmin()) {
            queryOrderPort.getById(id)
        } else {
            queryOrderPort.getByIdAndOwner(id, userPort.getUser().email)
        }
    }
}