package com.nemethlegtechnika.products.dto.company

import com.nemethlegtechnika.products.mapper.Default
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import java.io.Serializable

/**
 * DTO for {@link com.nemethlegtechnika.products.db.model.Company}
 */
data class UpdateCompanyDto @Default constructor(
    @field:NotNull val id: Long? = null,
    @field:NotEmpty val name: String? = null,
    @field:PositiveOrZero val discount: Double? = null,
    @field:PositiveOrZero val margin: Double? = null,
) : Serializable