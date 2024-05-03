package com.nemethlegtechnika.products.feature.service.repository

import com.nemethlegtechnika.products.model.CustomProperty
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CustomPropertyRepository: JpaRepository<CustomProperty, Long> {
    fun findByName(name: String): Optional<CustomProperty>
    fun findByNameIgnoreCase(name: String): Optional<CustomProperty>
    fun existsByName(name: String): Boolean
}