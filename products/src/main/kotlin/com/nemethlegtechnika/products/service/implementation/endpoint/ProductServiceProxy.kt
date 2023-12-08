package com.nemethlegtechnika.products.service.implementation.endpoint

import com.nemethlegtechnika.products.db.model.Product
import com.nemethlegtechnika.products.dto.product.GetProductDto
import com.nemethlegtechnika.products.mapper.ProductMapper
import com.nemethlegtechnika.products.service.interfaces.ProductService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class ProductServiceProxy(
    private val productService: ProductService,
    private val productMapper: ProductMapper
) {
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    fun getAll(companyName: String): List<GetProductDto> = productService.getAll(companyName).map { productMapper.map(it) }

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    fun getAll(): List<GetProductDto> = productService.getAll().map { productMapper.map(it) }

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    fun getAll(productIds: List<Long>): List<GetProductDto> = productService.getAll(productIds).map { productMapper.map(it) }

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    fun get(id: Long): GetProductDto = productService.get(id).let { productMapper.map(it) }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun create(companyName: String, product: Product): GetProductDto = productService.create(companyName, product).let { productMapper.map(it) }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun update(product: Product): GetProductDto = productService.update(product).let { productMapper.map(it) }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun addTag(productId: Long, tagId: Long): GetProductDto = productService.addTag(productId, tagId).let { productMapper.map(it) }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun removeTag(productId: Long, tagId: Long): GetProductDto = productService.removeTag(productId, tagId).let { productMapper.map(it) }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun delete(id: Long) = productService.delete(id)
}