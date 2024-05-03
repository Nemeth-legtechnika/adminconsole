package com.nemethlegtechnika.products.dto.tag

import com.nemethlegtechnika.products.mapper.Default
import com.nemethlegtechnika.products.feature.constant.Constants
import com.nemethlegtechnika.products.feature.validation.NotEmptyOrNull
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import java.io.Serializable

/**
 * DTO for {@link com.nemethlegtechnika.products.db.model.Tag}
 */
data class UpdateTagDto @Default constructor(
    @field:NotNull val id: Long? = null,
    @field:NotEmptyOrNull val name: String? = null,
    @field:Pattern(regexp = Constants.COLOR_REGEX) @field:NotEmptyOrNull val color: String? = null
) : Serializable