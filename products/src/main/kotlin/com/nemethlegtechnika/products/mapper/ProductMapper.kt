package com.nemethlegtechnika.products.mapper

import com.nemethlegtechnika.products.db.model.CustomProperty
import com.nemethlegtechnika.products.db.model.Product
import com.nemethlegtechnika.products.dto.product.CreateProductDto
import com.nemethlegtechnika.products.dto.product.GetProductDto
import com.nemethlegtechnika.products.dto.product.UpdateProductDto
import com.nemethlegtechnika.products.dto.property.UpdatePropertyDto
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = [TagMapper::class, ProductMapper::class])
abstract class ProductMapper {
    abstract fun map(createProductDto: CreateProductDto): Product
    @Mapping(source = "company.name", target = "companyName")
    @Mapping(source = "group.name", target = "groupName")
    abstract fun map(product: Product): GetProductDto

    abstract fun map(updateProductDto: UpdateProductDto): Product

    abstract fun toEntity(updatePropertyDto: UpdatePropertyDto): CustomProperty

    abstract fun toDto(customProperty: CustomProperty): UpdatePropertyDto

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract fun partialUpdate(updatePropertyDto: UpdatePropertyDto, @MappingTarget customProperty: CustomProperty): CustomProperty

    abstract fun toEntity1(getProductDto: GetProductDto): Product

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract fun partialUpdate1(getProductDto: GetProductDto, @MappingTarget product: Product): Product
}