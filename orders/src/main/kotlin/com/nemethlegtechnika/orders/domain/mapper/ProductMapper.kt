package com.nemethlegtechnika.orders.domain.mapper

import com.nemethlegtechnika.common.exception.BackendException
import com.nemethlegtechnika.orders.domain.model.Company
import com.nemethlegtechnika.orders.domain.model.Product
import com.nemethlegtechnika.orders.domain.model.Tag
import com.nemethlegtechnika.orders.feature.client.ProductResponse

fun ProductResponse.toProduct(quantityProvider: (Long) -> Int): Product {
    return Product(
        id = this.id ?: throw BackendException("ProductResponse id cannot be null"),
        name = this.name ?: "",
        number = this.number ?: "",
        description = this.description ?: "",
        listPrice = this.listPrice ?: 0L,
        purchasePrice = this.purchasePrice ?: 0L,
        sellPrice = this.sellPrice ?: 0L,
        quantity = quantityProvider(this.id),
        company = Company(
            name = this.companyName ?: ""
        ),
        tags = this.tags.map { Tag(name = it.name, color = it.color) }.toMutableList()
    )
}