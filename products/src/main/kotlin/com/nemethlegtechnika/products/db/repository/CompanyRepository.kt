package com.nemethlegtechnika.products.db.repository

import com.nemethlegtechnika.products.db.model.Company
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CompanyRepository: JpaRepository<Company, Long> {
    fun findByName(name: String): Optional<Company>
}