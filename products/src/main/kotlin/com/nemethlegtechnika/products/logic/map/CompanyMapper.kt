package com.nemethlegtechnika.products.logic.map

import com.nemethlegtechnika.products.api.dto.CompanyDto
import com.nemethlegtechnika.products.db.model.Company
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface CompanyMapper {
    companion object {
        val INSTANCE: CompanyMapper = Mappers.getMapper(CompanyMapper::class.java)
    }

    fun map(company: Company): CompanyDto
    fun map(company: CompanyDto): Company
    fun map(companies: List<Company>): List<CompanyDto>
}