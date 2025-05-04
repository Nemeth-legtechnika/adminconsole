package com.nemethlegtechnika.products.dto.property

import com.nemethlegtechnika.common.validation.NotEmptyOrNull
import com.nemethlegtechnika.products.mapper.Default
import java.io.Serializable

/**
 * DTO for {@link com.nemethlegtechnika.products.db.model.CustomProperty}
 */
data class UpdatePropertyDto @Default constructor(
    @field:NotEmptyOrNull val name: String? = null,
    @field:NotEmptyOrNull val value: String? = null,
) : Serializable