package com.nemethlegtechnika.orders.domain.command

import com.nemethlegtechnika.orders.domain.dto.OrderDto
import com.nemethlegtechnika.orders.domain.dto.OwnerDto
import com.trendyol.kediatr.Command
import com.trendyol.kediatr.CommandWithResult

data class ProductQuantity(
    val id: Long,
    val quantity: Int,
)

data class CreateOrder(
    val owner: OwnerDto,
    val products: List<ProductQuantity>,
): CommandWithResult<OrderDto>

data class UpdateOrder(
    val id: String,
    val owner: OwnerDto,
    val products: List<ProductQuantity>,
): CommandWithResult<OrderDto>

data class DeleteOrder(
    val id: String,
): Command