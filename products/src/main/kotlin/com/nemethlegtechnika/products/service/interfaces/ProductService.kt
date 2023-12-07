package com.nemethlegtechnika.products.service.interfaces

import com.nemethlegtechnika.products.db.model.Product

interface ProductService {
    fun getAll(companyName: String): List<Product>
    fun getAll(): List<Product>
    fun getAll(productIds: List<Long>): List<Product>
    fun get(id: Long): Product
    fun create(companyName: String, product: Product): Product
    fun update(product: Product): Product
    fun addTag(productId: Long, tagId: Long): Product
    fun removeTag(productId: Long, tagId: Long): Product
    fun delete(id: Long)
}