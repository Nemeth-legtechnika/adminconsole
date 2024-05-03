package com.nemethlegtechnika.products.feature.service.repository

import com.nemethlegtechnika.products.model.ProductGroup
import org.springframework.data.jpa.repository.JpaRepository

interface GroupRepository: JpaRepository<ProductGroup, Long> {
    fun existsByName(name: String): Boolean
}