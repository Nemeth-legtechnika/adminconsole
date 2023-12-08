package com.nemethlegtechnika.products.dto.company

import com.nemethlegtechnika.products.mapper.Default
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.PositiveOrZero
import java.io.Serializable

/**
 * DTO for {@link com.nemethlegtechnika.products.db.model.Company}
 */
data class CreateCompanyDto @Default constructor(
    @field:NotBlank val name: String = "",
    @field:PositiveOrZero val discount: Double = 0.0,
    @field:PositiveOrZero val margin: Double = 0.0
) : Serializable