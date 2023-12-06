package com.nemethlegtechnika.products.dto.product

import com.nemethlegtechnika.products.mapper.Default
import java.io.Serializable

/**
 * DTO for {@link com.nemethlegtechnika.products.db.model.Product}
 */
data class ProductInfoDto @Default constructor(
    val id: Long? = null,
    val name: String? = null,
    val number: String? = null
) : Serializable