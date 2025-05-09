package com.nemethlegtechnika.orders.domain.mapper

import com.nemethlegtechnika.common.exception.BackendException
import com.nemethlegtechnika.orders.domain.command.CreateOrder
import com.nemethlegtechnika.orders.domain.command.UpdateOrder
import com.nemethlegtechnika.orders.util.valueOfOrThrow
import com.nemethlegtechnika.orders.feature.port.CreateOrder as AdapterCreateOrder
import com.nemethlegtechnika.orders.feature.port.UpdateOrder as AdapterUpdateOrder

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