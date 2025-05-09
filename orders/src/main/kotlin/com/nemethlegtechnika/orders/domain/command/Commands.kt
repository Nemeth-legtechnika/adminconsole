package com.nemethlegtechnika.orders.domain.command

import com.nemethlegtechnika.orders.domain.dto.OwnerDto

data class ProductQuantity(
    val id: Long,
    val quantity: Int,
)

data class CreateOrder(
    val owner: OwnerDto,
    val products: List<ProductQuantity>,
)

data class UpdateOrder(
    val id: String,
    val owner: OwnerDto,
    val products: List<ProductQuantity>,
    val status: String,
)