package com.nemethlegtechnika.products.mapper

import com.nemethlegtechnika.products.db.model.CustomProperty
import com.nemethlegtechnika.products.dto.property.GetPropertyDto
import com.nemethlegtechnika.products.dto.property.UpdatePropertyDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface PropertyMapper {
    fun map(customProperty: CustomProperty): GetPropertyDto
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "id", ignore = true)
    fun map(customProperty: UpdatePropertyDto): CustomProperty
}