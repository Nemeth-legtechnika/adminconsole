package com.nemethlegtechnika.products.mapper

import com.nemethlegtechnika.products.db.model.Product
import com.nemethlegtechnika.products.dto.product.CreateProductDto
import com.nemethlegtechnika.products.dto.product.GetProductDto
import com.nemethlegtechnika.products.dto.product.UpdateProductDto
import com.nemethlegtechnika.products.service.interfaces.CompanyService
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = [TagMapper::class, CompanyService::class])
interface ProductMapper {
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "id", ignore = true)
    fun map(createProductDto: CreateProductDto): Product
    @Mapping(source = "company.name", target = "companyName")
    @Mapping(source = "group.name", target = "groupName")
    fun map(product: Product): GetProductDto
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "group", ignore = true)
    fun map(updateProductDto: UpdateProductDto): Product
}