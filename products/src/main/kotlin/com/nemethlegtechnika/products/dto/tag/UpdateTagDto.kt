package com.nemethlegtechnika.products.dto.tag

import com.nemethlegtechnika.products.mapper.Default
import com.nemethlegtechnika.products.service.constant.Constants
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import java.io.Serializable

/**
 * DTO for {@link com.nemethlegtechnika.products.db.model.Tag}
 */
data class UpdateTagDto @Default constructor(
    @field:NotNull val id: Long? = null,
    @field:NotEmpty val name: String? = null,
    @field:Pattern(regexp = Constants.COLOR_REGEX) @field:NotEmpty val color: String? = null
) : Serializable