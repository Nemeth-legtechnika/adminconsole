package com.nemethlegtechnika.orders.feature.service

import com.nemethlegtechnika.orders.domain.command.ProductQuantity
import com.nemethlegtechnika.orders.domain.model.Order
import com.nemethlegtechnika.orders.feature.adapter.QueryOrderAdapter
import com.nemethlegtechnika.orders.feature.adapter.RequestProductAdapter
import com.nemethlegtechnika.orders.feature.adapter.UpdateOrderAdapter
import com.nemethlegtechnika.orders.feature.usecase.SyncOrderUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service

@Service
class SyncOrderService(
    private val updateOrderAdapter: UpdateOrderAdapter,
    private val queryOrderAdapter: QueryOrderAdapter,
    private val requestProductAdapter: RequestProductAdapter,
): SyncOrderUseCase {
    override suspend fun syncAll(ids: List<String>): List<Order> = coroutineScope {
        ids.ifEmpty {
            queryOrderAdapter.getAllInProgressOrder().map { it.id!! }
        }.map { id ->
            async {
                syncById(id)
            }
        }.awaitAll()
    }

    override suspend fun syncById(id: String): Order =
        queryOrderAdapter.getInProgressById(id).let { order ->
            order.copy(
                products = requestProductAdapter.getProducts(
                    order.products.map { ProductQuantity(it.id, it.quantity) }
                ).toMutableList()
            ).also {
                updateOrderAdapter.updateOrder(it)
            }
    }
}