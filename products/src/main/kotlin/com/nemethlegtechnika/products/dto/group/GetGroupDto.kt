package com.nemethlegtechnika.products.dto.group

import com.nemethlegtechnika.products.dto.product.ProductInfoDto
import com.nemethlegtechnika.products.mapper.Default
import java.io.Serializable
import java.util.*

/**
 * DTO for {@link com.nemethlegtechnika.products.db.model.ProductGroup}
 */
data class GetGroupDto @Default constructor(
    val id: Long? = null,
    val createdAt: Date? = null,
    val updatedAt: Date? = null,
    val name: String? = null,
    val products: MutableList<ProductInfoDto> = mutableListOf()
) : Serializable