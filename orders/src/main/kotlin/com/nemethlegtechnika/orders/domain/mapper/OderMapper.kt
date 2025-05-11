package com.nemethlegtechnika.orders.domain.mapper

import com.nemethlegtechnika.common.exception.BackendException
import com.nemethlegtechnika.orders.domain.dto.CompanyDto
import com.nemethlegtechnika.orders.domain.dto.OrderDto
import com.nemethlegtechnika.orders.domain.dto.OwnerDto
import com.nemethlegtechnika.orders.domain.dto.ProductDto
import com.nemethlegtechnika.orders.domain.dto.TagDto
import com.nemethlegtechnika.orders.domain.model.Company
import com.nemethlegtechnika.orders.domain.model.Order
import com.nemethlegtechnika.orders.domain.model.Owner
import com.nemethlegtechnika.orders.domain.model.Product
import com.nemethlegtechnika.orders.domain.model.Tag
import com.nemethlegtechnika.orders.feature.usecase.NotificationOrder
import com.nemethlegtechnika.orders.feature.usecase.NotificationProduct
import com.nemethlegtechnika.orders.util.valueOfOrThrow

fun Order.toOrderDto(): OrderDto {
    return OrderDto(
        id = this.id,
        owner = this.owner?.toOwnerDto(),
        products = this.products.map { it.toProductDto() },
        status = this.status.name,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}

fun OrderDto.toOrder(): Order {
    return Order(
        id = this.id,
        owner = this.owner?.toOwner(),
        products = this.products.map { it.toProduct() }.toMutableList(),
        status = valueOfOrThrow(this.status) { throw BackendException("Invalid order status: ${this.status}") },
    )
}

fun Owner.toOwnerDto(): OwnerDto {
    return OwnerDto(
        email = this.email,
        fullName = this.fullName,
        username = this.username
    )
}

fun OwnerDto.toOwner(): Owner {
    return Owner(
        email = this.email,
        fullName = this.fullName,
        username = this.username
    )
}

fun Product.toProductDto(): ProductDto {
    return ProductDto(
        id = this.id,
        name = this.name,
        number = this.number,
        description = this.description,
        listPrice = this.listPrice,
        purchasePrice = this.purchasePrice,
        sellPrice = this.sellPrice,
        quantity = this.quantity,
        company = this.company.toCompanyDto(),
        tags = this.tags.map { it.toTagDto() }
    )
}

fun ProductDto.toProduct(): Product {
    return Product(
        id = this.id,
        name = this.name,
        number = this.number,
        description = this.description,
        listPrice = this.listPrice,
        purchasePrice = this.purchasePrice,
        sellPrice = this.sellPrice,
        quantity = this.quantity,
        company = this.company.toCompany(),
        tags = this.tags.map { it.toTag() }.toMutableList()
    )
}

fun Company.toCompanyDto(): CompanyDto {
    return CompanyDto(
        name = this.name
    )
}

fun CompanyDto.toCompany(): Company {
    return Company(
        name = this.name
    )
}

fun Tag.toTagDto(): TagDto {
    return TagDto(
        name = this.name,
        color = this.color
    )
}

fun TagDto.toTag(): Tag {
    return Tag(
        name = this.name,
        color = this.color
    )
}

fun Order.toNotification(): NotificationOrder {
    return NotificationOrder(
        owner = this.owner ?: throw BackendException("Owner is null"),
        products = this.products.map { it.toNotification() }
    )
}

fun Product.toNotification(): NotificationProduct {
    return NotificationProduct(
        name = this.name,
        description = this.description,
        price = this.sellPrice,
        quantity = this.quantity
    )
}