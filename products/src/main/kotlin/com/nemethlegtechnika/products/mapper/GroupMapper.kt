package com.nemethlegtechnika.products.mapper

import com.nemethlegtechnika.products.db.model.ProductGroup
import com.nemethlegtechnika.products.dto.group.CreateGroupDto
import com.nemethlegtechnika.products.dto.group.GetGroupDto
import com.nemethlegtechnika.products.dto.group.UpdateGroupDto
import com.nemethlegtechnika.products.service.interfaces.ProductService
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = [ProductService::class])
interface GroupMapper {
    @Mapping(source = "company.name", target = "companyName")
    fun map(productGroup: ProductGroup): GetGroupDto
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "products", ignore = true)
    fun map(updateGroupDto: UpdateGroupDto): ProductGroup
    @Mapping(source = "companyName", target = "company.name")
    @Mapping(source = "productIds", target = "products")
    @Mapping(target = "id", ignore = true)
    fun map(createGroupDto: CreateGroupDto): ProductGroup
}