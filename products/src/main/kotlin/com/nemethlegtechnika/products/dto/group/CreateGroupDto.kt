package com.nemethlegtechnika.products.dto.group

import com.nemethlegtechnika.products.mapper.Default
import jakarta.validation.constraints.NotBlank
import java.io.Serializable

/**
 * DTO for {@link com.nemethlegtechnika.products.db.model.ProductGroup}
 */
data class CreateGroupDto @Default constructor(
    @field:NotBlank val name: String? = null,
    val companyName: String? = null,
    val productIds: MutableList<Long>?
) : Serializable