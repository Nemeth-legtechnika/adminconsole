package com.nemethlegtechnika.products.api.dto

data class ProductDto(
    val id: Long,
    val name: String,
    val number: String,
    val type: String,
    val listPrice: Long = 0,
    val sellPrice: Long = 0,
    val purchasePrice: Long = 0,
    val description: String? = null,
    val discount: Double? = null,
    val margin: Double? = null,
)