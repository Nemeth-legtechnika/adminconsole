package com.nemethlegtechnika.products.mapper

import com.nemethlegtechnika.products.db.model.Tag
import com.nemethlegtechnika.products.dto.tag.CreateTagDto
import com.nemethlegtechnika.products.dto.tag.GetTagDto
import com.nemethlegtechnika.products.dto.tag.UpdateTagDto
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
abstract class TagMapper {
    abstract fun map(createTagDto: CreateTagDto): Tag
    abstract fun map(tag: Tag): GetTagDto
    abstract fun map(updateTagDto: UpdateTagDto): Tag
}