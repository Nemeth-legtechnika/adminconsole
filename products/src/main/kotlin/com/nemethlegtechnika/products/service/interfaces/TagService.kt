package com.nemethlegtechnika.products.service.interfaces

import com.nemethlegtechnika.products.db.model.Tag

interface TagService {
    fun getAll(): List<Tag>
    fun get(id: Long): Tag
    fun create(tag: Tag): Tag
    fun update(tag: Tag): Tag
    fun delete(id: Long)
}