package com.nemethlegtechnika.orders.feature.service

import com.nemethlegtechnika.orders.domain.mapper.toOder
import com.nemethlegtechnika.orders.domain.mapper.toOrder
import com.nemethlegtechnika.orders.domain.model.Order
import com.nemethlegtechnika.orders.feature.port.CreateOrderPort
import com.nemethlegtechnika.orders.feature.port.DeleteOrderPort
import com.nemethlegtechnika.orders.feature.port.RequestProductPort
import com.nemethlegtechnika.orders.feature.port.UpdateOrderPort
import com.nemethlegtechnika.orders.feature.usecase.CreateOrder
import com.nemethlegtechnika.orders.feature.usecase.ModifyOrderUseCase
import com.nemethlegtechnika.orders.feature.usecase.UpdateOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class ModifyOrderService(
    private val createOrderPort: CreateOrderPort,
    private val updateOrderPort: UpdateOrderPort,
    private val deleteOrderPort: DeleteOrderPort,
    private val requestProductPort: RequestProductPort,
): ModifyOrderUseCase {
    override suspend fun createOrder(order: CreateOrder): Order = withContext(Dispatchers.IO) {
        order.toOder { productQuantities ->
            requestProductPort.getProducts(productQuantities)
        }.let {
            createOrderPort.createOrder(it)
        }
    }

    override suspend fun updateOrder(order: UpdateOrder): Order = withContext(Dispatchers.IO) {
        order.toOrder { productQuantities ->
            requestProductPort.getProducts(productQuantities)
        }.let {
            updateOrderPort.updateOrder(it)
        }
    }

    override suspend fun deleteOrder(id: String) = withContext(Dispatchers.IO) {
        deleteOrderPort.deleteOrder(id)
    }
}