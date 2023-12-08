package com.nemethlegtechnika.products.dto.tag

import com.nemethlegtechnika.products.mapper.Default
import java.io.Serializable

/**
 * DTO for {@link com.nemethlegtechnika.products.db.model.Tag}
 */
data class GetTagDto @Default constructor(
    val id: Long? = null,
    val name: String? = null,
    val color: String? = null
) : Serializable