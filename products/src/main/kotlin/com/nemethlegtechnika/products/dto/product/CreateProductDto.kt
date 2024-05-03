package com.nemethlegtechnika.products.dto.product

import com.nemethlegtechnika.products.mapper.Default
import com.nemethlegtechnika.products.feature.validation.NotEmptyOrNull
import com.nemethlegtechnika.products.feature.validation.PositiveOrZeroOrNull
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.PositiveOrZero
import java.io.Serializable

/**
 * DTO for {@link com.nemethlegtechnika.products.db.model.Product}
 */
data class CreateProductDto @Default constructor(
    @field:NotBlank val name: String? = null,
    @field:NotBlank val companyName: String? = null,
    @field:NotBlank val number: String? = null,
    @field:NotEmptyOrNull val description: String? = null,
    @field:PositiveOrZero val listPrice: Long? = null,
    @field:PositiveOrZeroOrNull val discount: Double? = null,
    @field:PositiveOrZeroOrNull val margin: Double? = null,
) : Serializable