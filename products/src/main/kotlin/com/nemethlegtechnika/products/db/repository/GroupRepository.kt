package com.nemethlegtechnika.products.db.repository

import com.nemethlegtechnika.products.db.model.ProductGroup
import org.springframework.data.jpa.repository.JpaRepository

interface GroupRepository: JpaRepository<ProductGroup, Long> {
    fun existsByName(name: String): Boolean
}