package com.nemethlegtechnika.products.feature.service.interfaces

import com.nemethlegtechnika.products.model.ProductGroup

interface GroupService {
    fun getAll(): List<ProductGroup>
    fun get(id: Long): ProductGroup
    fun create(group: ProductGroup): ProductGroup
    fun update(group: ProductGroup): ProductGroup
    fun addProduct(productId: Long, groupId: Long): ProductGroup
    fun removeProduct(productId: Long, groupId: Long): ProductGroup
    fun exists(name: String?): Boolean
    fun delete(id: Long)
}