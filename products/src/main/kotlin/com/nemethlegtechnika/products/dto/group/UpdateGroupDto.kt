package com.nemethlegtechnika.products.dto.group

import com.nemethlegtechnika.common.validation.NotEmptyOrNull
import com.nemethlegtechnika.products.mapper.Default
import jakarta.validation.constraints.NotNull
import java.io.Serializable

/**
 * DTO for {@link com.nemethlegtechnika.products.db.model.ProductGroup}
 */
data class UpdateGroupDto @Default constructor(
    @field:NotNull val id: Long? = null,
    @field:NotEmptyOrNull val name: String? = null,
) : Serializable