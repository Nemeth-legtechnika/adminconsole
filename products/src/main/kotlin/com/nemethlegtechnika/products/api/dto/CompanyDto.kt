package com.nemethlegtechnika.products.api.dto

data class CompanyDto(
    val id: Long,
    val name: String,
    val discount: Double? = null,
    val margin: Double? = null,
    val products: List<ProductDto> = emptyList()
)