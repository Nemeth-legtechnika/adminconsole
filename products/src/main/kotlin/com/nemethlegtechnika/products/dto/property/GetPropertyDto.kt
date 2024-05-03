package com.nemethlegtechnika.products.dto.property

import com.nemethlegtechnika.products.model.Type
import com.nemethlegtechnika.products.mapper.Default
import java.io.Serializable
import java.util.*

/**
 * DTO for {@link com.nemethlegtechnika.products.db.model.CustomProperty}
 */
data class GetPropertyDto @Default constructor(
    val id: Long? = null,
    val createdAt: Date? = null,
    val updatedAt: Date? = null,
    val name: String? = null,
    val value: String = "",
    val type: Type = Type.STRING
) : Serializable