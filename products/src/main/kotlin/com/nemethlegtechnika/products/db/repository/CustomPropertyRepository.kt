package com.nemethlegtechnika.products.db.repository

import com.nemethlegtechnika.products.db.model.CustomProperty
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomPropertyRepository: JpaRepository<CustomProperty, Long> {
    fun findByName(name: String): Optional<CustomProperty>
    fun findByNameIgnoreCase(name: String): Optional<CustomProperty>
    fun existsByName(name: String): Boolean
}