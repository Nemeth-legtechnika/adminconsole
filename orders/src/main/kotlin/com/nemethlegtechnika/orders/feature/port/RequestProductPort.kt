package com.nemethlegtechnika.orders.feature.port

import com.nemethlegtechnika.orders.domain.command.ProductQuantity
import com.nemethlegtechnika.orders.domain.model.Product

interface RequestProductPort {
    suspend fun getProducts(products: List<ProductQuantity>): List<Product>
}