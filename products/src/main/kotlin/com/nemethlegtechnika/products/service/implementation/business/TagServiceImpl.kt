package com.nemethlegtechnika.products.service.implementation.business

import com.nemethlegtechnika.products.db.model.Tag
import com.nemethlegtechnika.products.db.repository.TagRepository
import com.nemethlegtechnika.products.service.interfaces.TagService
import com.nemethlegtechnika.products.util.findByIdOrThrow
import com.nemethlegtechnika.products.util.update
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class TagServiceImpl(private val tagRepository: TagRepository) : TagService {
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun getAll(): List<Tag> = tagRepository.findAll()

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun get(id: Long) = tagRepository.findByIdOrThrow(id)

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun create(tag: Tag) = tagRepository.saveAndFlush(tag)

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun update(tag: Tag): Tag {
        return tagRepository.update(tag.id) {
            this.name = tag.name ?: this.name
            this.color = tag.color ?: this.color
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun delete(id: Long) {
        tagRepository.deleteById(id)
    }
}