package com.nemethlegtechnika.products.service.interfaces

import com.nemethlegtechnika.products.db.model.Company

interface CompanyService {
    fun getAll(): List<Company>
    fun get(name: String): Company
    fun get(id: Long): Company
    fun create(company: Company): Company
    fun update(company: Company): Company
    fun exists(name: String?): Boolean
    fun delete(id: Long)
}