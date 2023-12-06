package com.nemethlegtechnika.products.service.interfaces

import com.nemethlegtechnika.products.db.model.ProductGroup

interface GroupService {
    fun getAll(): List<ProductGroup>
    fun get(id: Long): ProductGroup
    fun getDefaultGroup(companyName: String): ProductGroup
    fun createDefaultGroup(companyName: String): ProductGroup
    fun create(group: ProductGroup): ProductGroup
    fun update(group: ProductGroup): ProductGroup
    fun addProduct(productId: Long, groupId: Long): ProductGroup
    fun removeProduct(productId: Long, groupId: Long): ProductGroup
    fun delete(id: Long)
}