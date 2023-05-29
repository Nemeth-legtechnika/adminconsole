package com.nemethlegtechnika.products.api.dto

import com.nemethlegtechnika.products.logic.config.Configuration

data class CompanyDto(
    val id: Long,
    val name: String,
    val discount: Double = Configuration.DEFAULT_DISCOUNT,
    val margin: Double = Configuration.DEFAULT_MARGIN,
    val products: List<ProductDto> = emptyList()
)