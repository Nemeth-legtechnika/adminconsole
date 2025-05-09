package com.nemethlegtechnika.orders.feature.client

import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange

data class TagResponse(
    val name: String,
    val color: String,
)

data class ProductResponse(
    val id: Long? = null,
    val name: String? = null,
    val number: String? = null,
    val description: String? = null,
    val listPrice: Long? = null,
    val discount: Double? = null,
    val margin: Double? = null,
    val companyName: String? = null,
    val tags: List<TagResponse> = emptyList(),
    val purchasePrice: Long? = null,
    val sellPrice: Long? = null,
)

interface ProductClient {
    @GetExchange("/api/product")
    fun getProducts(
        @RequestParam ids: List<Long>,
        @RequestHeader("Authorization") accessToken: String
    ): List<ProductResponse>
}