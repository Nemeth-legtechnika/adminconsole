package com.nemethlegtechnika.products.mapper

import com.nemethlegtechnika.products.db.model.CustomProperty
import com.nemethlegtechnika.products.dto.property.GetPropertyDto
import com.nemethlegtechnika.products.dto.property.UpdatePropertyDto
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
abstract class PropertyMapper {
    abstract fun map(customProperty: CustomProperty): GetPropertyDto
    abstract fun map(customProperty: UpdatePropertyDto): CustomProperty
}