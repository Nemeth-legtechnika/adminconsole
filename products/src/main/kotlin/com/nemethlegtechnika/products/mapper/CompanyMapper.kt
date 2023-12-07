package com.nemethlegtechnika.products.mapper

import com.nemethlegtechnika.products.db.model.Company
import com.nemethlegtechnika.products.dto.company.CreateCompanyDto
import com.nemethlegtechnika.products.dto.company.GetCompanyDto
import com.nemethlegtechnika.products.dto.company.GetCompanyProductDto
import com.nemethlegtechnika.products.dto.company.UpdateCompanyDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = [ProductMapper::class])
interface CompanyMapper {
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "groups", ignore = true)
    @Mapping(target = "id", ignore = true)
    fun map(createCompanyDto: CreateCompanyDto): Company
    fun map(company: Company): GetCompanyDto
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "groups", ignore = true)
    fun map(updateCompanyDto: UpdateCompanyDto): Company
    fun mapWithProduct(company: Company): GetCompanyProductDto
}