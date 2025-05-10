package com.nemethlegtechnika.orders.domain.mapper

import com.nemethlegtechnika.common.exception.BackendException
import com.nemethlegtechnika.orders.domain.command.CreateOrder
import com.nemethlegtechnika.orders.domain.command.ProductQuantity
import com.nemethlegtechnika.orders.domain.command.UpdateOrder
import com.nemethlegtechnika.orders.domain.model.Order
import com.nemethlegtechnika.orders.domain.model.OrderStatus
import com.nemethlegtechnika.orders.domain.model.Product
import com.nemethlegtechnika.orders.util.valueOfOrThrow
import com.nemethlegtechnika.orders.feature.usecase.CreateOrder as AdapterCreateOrder
import com.nemethlegtechnika.orders.feature.usecase.UpdateOrder as AdapterUpdateOrder

fun CreateOrder.toAdapter(): AdapterCreateOrder {
    return AdapterCreateOrder(
        owner = this.owner.toOwner(),
        products = this.products
    )
}

fun UpdateOrder.toAdapter(): AdapterUpdateOrder {
    return AdapterUpdateOrder(
        id = this.id,
        owner = this.owner.toOwner(),
        products = this.products,
        status = valueOfOrThrow(this.status) { throw BackendException("Invalid order status: ${this.status}") },
    )
}

suspend fun AdapterCreateOrder.toOder(productProvider: suspend (List<ProductQuantity>) -> List<Product>): Order {
    return Order(
        owner = this.owner,
        products = productProvider(this.products).toMutableList(),
        status = OrderStatus.IN_PROGRESS
    )
}

suspend fun AdapterUpdateOrder.toOrder(productProvider: suspend (List<ProductQuantity>) -> List<Product>): Order {
    return Order(
        id = this.id,
        owner = this.owner,
        products = productProvider(this.products).toMutableList(),
        status = this.status
    )
}