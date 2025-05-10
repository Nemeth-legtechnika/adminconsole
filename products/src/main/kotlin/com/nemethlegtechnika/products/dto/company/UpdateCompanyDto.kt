package com.nemethlegtechnika.products.dto.company

import com.nemethlegtechnika.common.validation.NotEmptyOrNull
import com.nemethlegtechnika.common.validation.PositiveOrZeroOrNull
import com.nemethlegtechnika.products.mapper.Default
import jakarta.validation.constraints.NotNull
import java.io.Serializable

/**
 * DTO for {@link com.nemethlegtechnika.products.db.model.Company}
 */
data class UpdateCompanyDto @Default constructor(
    @field:NotNull val id: Long? = null,
    @field:NotEmptyOrNull val name: String? = null,
    @field:PositiveOrZeroOrNull val discount: Double? = null,
    @field:PositiveOrZeroOrNull val margin: Double? = null,
) : Serializable

