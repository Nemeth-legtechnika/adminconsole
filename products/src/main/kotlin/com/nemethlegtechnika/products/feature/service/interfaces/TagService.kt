package com.nemethlegtechnika.products.feature.service.interfaces

import com.nemethlegtechnika.products.model.Tag

interface TagService {
    fun getAll(): List<Tag>
    fun get(id: Long): Tag
    fun create(tag: Tag): Tag
    fun update(tag: Tag): Tag
    fun delete(id: Long)
}