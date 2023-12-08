package com.nemethlegtechnika.products.dto.product

import com.nemethlegtechnika.products.dto.tag.GetTagDto
import com.nemethlegtechnika.products.mapper.Default
import java.io.Serializable
import java.util.*

/**
 * DTO for {@link com.nemethlegtechnika.products.db.model.Product}
 */
data class GetProductDto @Default constructor(
    val id: Long? = null,
    val createdAt: Date? = null,
    val updatedAt: Date? = null,
    val name: String? = null,
    val number: String? = null,
    val description: String? = null,
    val listPrice: Long? = null,
    val discount: Double? = null,
    val margin: Double? = null,
    val companyName: String? = null,
    val tags: List<GetTagDto> = emptyList(),
    val groupName: String? = null,
    val purchasePrice: Long? = null,
    val sellPrice: Long? = null,
) : Serializable