package com.nemethlegtechnika.orders.domain.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

data class Owner(
    val email: String,
    val fullName: String,
    val username: String,
)

data class Company(
    val name: String,
)

data class Tag(
    val name: String,
    val color: String,
)

data class Product(
    val id: Long,
    val name: String,
    val number: String,
    val description: String,
    val listPrice: Long,
    val purchasePrice: Long,
    val sellPrice: Long,
    val quantity: Int,
    val company: Company,
    val tags: MutableList<Tag> = mutableListOf(),
)

enum class OrderStatus {
    IN_PROGRESS,
    DONE,
    CANCELLED,
}

@Document(collection = "orders")
data class Order (
    @Id
    val id: String? = null,
    val owner: Owner? = null,
    val products: MutableList<Product>,
    val status: OrderStatus = OrderStatus.IN_PROGRESS,

    @CreatedDate
    val createdAt: Instant? = null,

    @LastModifiedDate
    val updatedAt: Instant? = null,
)