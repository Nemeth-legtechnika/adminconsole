package com.nemethlegtechnika.products.mapper

import com.nemethlegtechnika.products.db.model.Company
import com.nemethlegtechnika.products.dto.company.CreateCompanyDto
import com.nemethlegtechnika.products.dto.company.GetCompanyDto
import com.nemethlegtechnika.products.dto.company.GetCompanyProductDto
import com.nemethlegtechnika.products.dto.company.UpdateCompanyDto
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = [ProductMapper::class])
abstract class CompanyMapper {
    abstract fun map(createCompanyDto: CreateCompanyDto): Company
    abstract fun map(company: Company): GetCompanyDto
    abstract fun map(updateCompanyDto: UpdateCompanyDto): Company
    abstract fun mapWithProduct(company: Company): GetCompanyProductDto
}