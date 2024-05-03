package com.nemethlegtechnika.products.feature.service.repository

import com.nemethlegtechnika.products.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository: JpaRepository<Product, Long> {
    fun findAllByCompanyName(companyName: String): List<Product>
    fun findAllByIdIn(ids: List<Long>): List<Product>
}