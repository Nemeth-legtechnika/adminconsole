package com.nemethlegtechnika.products.dto.tag

import com.nemethlegtechnika.products.feature.constant.Constants.COLOR_REGEX
import com.nemethlegtechnika.products.mapper.Default
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.io.Serializable

/**
 * DTO for {@link com.nemethlegtechnika.products.db.model.Tag}
 */
data class CreateTagDto @Default constructor(
    @field:NotBlank val name: String? = null,
    @field:Pattern(regexp = COLOR_REGEX) @field:NotBlank val color: String? = null
): Serializable