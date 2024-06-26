package com.nemethlegtechnika.products.feature.service.interfaces

import com.nemethlegtechnika.products.model.Product

interface ProductService {
    fun getAll(companyName: String): List<Product>
    fun getAll(): List<Product>
    fun getAll(productIds: List<Long>): List<Product>
    fun get(id: Long): Product
    fun create(companyName: String, product: Product): Product
    fun update(product: Product): Product
    fun update(products: List<Product>): List<Product>
    fun addTag(productId: Long, tagId: Long): Product
    fun removeTag(productId: Long, tagId: Long): Product
    fun delete(id: Long)
    fun delete(ids: List<Long>)
}