package com.nemethlegtechnika.products.dto.company

import com.nemethlegtechnika.products.dto.product.GetProductDto
import com.nemethlegtechnika.products.mapper.Default
import java.io.Serializable

/**
 * DTO for {@link com.nemethlegtechnika.products.db.model.Company}
 */
data class GetCompanyProductDto @Default constructor(
    val id: Long? = null,
    val name: String? = null,
    val products: MutableList<GetProductDto> = mutableListOf()
) : Serializable