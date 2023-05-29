package com.nemethlegtechnika.products.logic.service

import com.nemethlegtechnika.products.db.model.Company

interface CompanyService {
     fun getCompany(id: Long): Company
     fun getCompanies(): List<Company>
     fun create(company: Company): Company
     fun updateCompany(company: Company)
     fun delete(id: Long)
}