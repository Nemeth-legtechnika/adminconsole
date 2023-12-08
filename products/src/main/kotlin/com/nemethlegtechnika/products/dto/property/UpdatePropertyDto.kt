package com.nemethlegtechnika.products.dto.property

import com.nemethlegtechnika.products.mapper.Default
import jakarta.validation.constraints.NotBlank
import java.io.Serializable

/**
 * DTO for {@link com.nemethlegtechnika.products.db.model.CustomProperty}
 */
data class UpdatePropertyDto @Default constructor(
    @field:NotBlank val name: String? = null,
    @field:NotBlank val value: String? = null,
) : Serializable