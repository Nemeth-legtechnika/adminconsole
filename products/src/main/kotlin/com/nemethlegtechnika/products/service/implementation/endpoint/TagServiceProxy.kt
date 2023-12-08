package com.nemethlegtechnika.products.service.implementation.endpoint

import com.nemethlegtechnika.products.db.model.Tag
import com.nemethlegtechnika.products.dto.tag.GetTagDto
import com.nemethlegtechnika.products.mapper.TagMapper
import com.nemethlegtechnika.products.service.interfaces.TagService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class TagServiceProxy(
    private val tagService: TagService,
    private val tagMapper: TagMapper,
) {
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    fun getAll(): List<GetTagDto> = tagService.getAll().map { tagMapper.map(it) }

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    fun get(id: Long): GetTagDto = tagService.get(id).let { tagMapper.map(it) }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun create(tag: Tag): GetTagDto = tagService.create(tag).let { tagMapper.map(it) }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun update(tag: Tag): GetTagDto = tagService.update(tag).let { tagMapper.map(it) }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun delete(id: Long) = tagService.delete(id)
}