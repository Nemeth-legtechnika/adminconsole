package com.nemethlegtechnika.products.mapper

import com.nemethlegtechnika.products.db.model.Tag
import com.nemethlegtechnika.products.dto.tag.CreateTagDto
import com.nemethlegtechnika.products.dto.tag.GetTagDto
import com.nemethlegtechnika.products.dto.tag.UpdateTagDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface TagMapper {

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "id", ignore = true)
    fun map(createTagDto: CreateTagDto): Tag
    fun map(tag: Tag): GetTagDto
    @Mapping(target = "product", ignore = true)
    fun map(updateTagDto: UpdateTagDto): Tag
}