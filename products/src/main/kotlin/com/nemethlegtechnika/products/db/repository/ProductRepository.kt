package com.nemethlegtechnika.products.db.repository

import com.nemethlegtechnika.products.db.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository: JpaRepository<Product, Long> {
    fun findAllByCompanyName(companyName: String): List<Product>
}