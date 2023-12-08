package com.nemethlegtechnika.products.dto.company

import com.nemethlegtechnika.products.mapper.Default
import java.io.Serializable
import java.util.*

/**
 * DTO for {@link com.nemethlegtechnika.products.db.model.Company}
 */
data class GetCompanyDto @Default constructor(
    val id: Long? = null,
    val createdAt: Date? = null,
    val updatedAt: Date? = null,
    val name: String = "",
    val discount: Double = 0.0,
    val margin: Double = 0.0
) : Serializable