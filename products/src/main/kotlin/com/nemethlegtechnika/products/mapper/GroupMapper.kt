package com.nemethlegtechnika.products.mapper

import com.nemethlegtechnika.products.model.ProductGroup
import com.nemethlegtechnika.products.dto.group.CreateGroupDto
import com.nemethlegtechnika.products.dto.group.GetGroupDto
import com.nemethlegtechnika.products.dto.group.UpdateGroupDto
import com.nemethlegtechnika.products.feature.service.interfaces.CompanyService
import com.nemethlegtechnika.products.feature.service.interfaces.ProductService
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = [ProductService::class, CompanyService::class])
interface GroupMapper {
    fun map(productGroup: ProductGroup): GetGroupDto

    @Mapping(target = "products", ignore = true)
    fun map(updateGroupDto: UpdateGroupDto): ProductGroup

    @Mapping(source = "productIds", target = "products")
    @Mapping(target = "id", ignore = true)
    fun map(createGroupDto: CreateGroupDto): ProductGroup
}