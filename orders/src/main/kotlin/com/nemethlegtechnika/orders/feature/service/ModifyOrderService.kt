package com.nemethlegtechnika.orders.feature.service

import com.nemethlegtechnika.orders.domain.model.Order
import com.nemethlegtechnika.orders.feature.port.CreateOrder
import com.nemethlegtechnika.orders.feature.port.CreateOrderPort
import com.nemethlegtechnika.orders.feature.port.DeleteOrderPort
import com.nemethlegtechnika.orders.feature.port.UpdateOrder
import com.nemethlegtechnika.orders.feature.port.UpdateOrderPort
import com.nemethlegtechnika.orders.feature.port.UserPort
import com.nemethlegtechnika.orders.feature.usecase.ModifyOrderUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class ModifyOrderService(
    private val createOrderPort: CreateOrderPort,
    private val updateOrderPort: UpdateOrderPort,
    private val deleteOrderPort: DeleteOrderPort,
    private val userPort: UserPort,
): ModifyOrderUseCase {
    override suspend fun createOrder(order: CreateOrder): Order = withContext(Dispatchers.IO) {
        createOrderPort.createOrder(order)
    }

    override suspend fun updateOrder(order: UpdateOrder): Order = withContext(Dispatchers.IO) {
        updateOrderPort.updateOrder(order)
    }

    override suspend fun deleteOrder(id: String) = withContext(Dispatchers.IO) {
        deleteOrderPort.deleteOrder(id)
    }
}