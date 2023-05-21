package com.nemethlegtechnika.products.db.repository

import com.nemethlegtechnika.products.db.model.Company
import org.springframework.data.jpa.repository.JpaRepository

interface CompanyRepository: JpaRepository<Company, Long> {
}