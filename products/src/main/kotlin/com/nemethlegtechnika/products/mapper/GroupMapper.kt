package com.nemethlegtechnika.products.mapper

import com.nemethlegtechnika.products.db.model.ProductGroup
import com.nemethlegtechnika.products.dto.group.CreateGroupDto
import com.nemethlegtechnika.products.dto.group.GetGroupDto
import com.nemethlegtechnika.products.dto.group.UpdateGroupDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants
import org.mapstruct.ReportingPolicy

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
abstract class GroupMapper {
    abstract fun map(productGroup: ProductGroup): GetGroupDto
    abstract fun map(updateGroupDto: UpdateGroupDto): ProductGroup
    @Mapping(source = "companyName", target = "company.name")
    abstract fun map(createGroupDto: CreateGroupDto): ProductGroup
}