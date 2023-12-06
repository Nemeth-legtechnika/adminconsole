package com.nemethlegtechnika.products.dto.product

import com.nemethlegtechnika.products.mapper.Default
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import java.io.Serializable

/**
 * DTO for {@link com.nemethlegtechnika.products.db.model.Product}
 */
data class UpdateProductDto @Default constructor(
    @field:NotNull val id: Long? = null,
    @field:NotBlank val name: String? = null,
    @field:NotBlank val number: String? = null,
    @field:NotBlank val description: String? = null,
    @field:PositiveOrZero val listPrice: Long? = null,
    @field:PositiveOrZero val discount: Double? = null,
    @field:PositiveOrZero val margin: Double? = null
) : Serializable