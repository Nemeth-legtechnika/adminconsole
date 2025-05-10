package com.nemethlegtechnika.orders.domain.dto

import java.time.Instant

data class OrderDto(
    val id: String?,
    val owner: OwnerDto?,
    val products: List<ProductDto>,
    val status: String,
    val createdAt: Instant?,
    val updatedAt: Instant?,
)

data class OwnerDto(
    val email: String,
    val fullName: String,
    val username: String,
)

data class ProductDto(
    val id: Long,
    val name: String,
    val number: String,
    val description: String,
    val listPrice: Long,
    val purchasePrice: Long,
    val sellPrice: Long,
    val quantity: Int,
    val company: CompanyDto,
    val tags: List<TagDto>,
)

data class CompanyDto(
    val name: String,
)

data class TagDto(
    val name: String,
    val color: String,
)