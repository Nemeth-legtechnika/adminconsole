package com.nemethlegtechnika.products.service.implementation

import com.nemethlegtechnika.products.db.model.Tag
import com.nemethlegtechnika.products.db.repository.TagRepository
import com.nemethlegtechnika.products.exception.EntityNotFoundException
import com.nemethlegtechnika.products.optional
import com.nemethlegtechnika.products.service.implementation.business.TagServiceImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class TagServiceTest {

    private val tagRepository: TagRepository = mockk()
    private val tagService = TagServiceImpl(tagRepository)

    @Test
    fun `Test get all tags`() {
        every { tagRepository.findAll() } returns listOf(
            Tag().apply {
                id = 1
                name = "Tag 1"
                color = "Color 1"
            },
            Tag().apply {
                id = 2
                name = "Tag 2"
                color = "Color 2"
            },
            Tag().apply {
                id = 3
                name = "Tag 3"
                color = "Color 3"
            },
        )

        val tags = tagService.getAll()
        assertEquals(3, tags.size)
        assertTrue(tags.any { it.name == "Tag 1" })
        assertTrue(tags.any { it.name == "Tag 2" })
        assertTrue(tags.any { it.name == "Tag 3" })
    }

    @Test
    fun `Test get tag by id`() {
        val tagId = 1L
        every { tagRepository.findById(tagId) } returns Tag().apply {
            id = tagId
            name = "Tag 1"
            color = "Color 1"
        }.optional

        val tag = tagService.get(tagId)
        assertEquals(tagId, tag.id)
        assertEquals("Tag 1", tag.name)
        assertEquals("Color 1", tag.color)
    }

    @Test
    fun `Test get tag by id not found`() {
        val tagId = 1L
        every { tagRepository.findById(tagId) } returns Optional.empty()

        val exception = assertThrows<EntityNotFoundException> { tagService.get(tagId) }
        assertEquals("Tag with id $tagId not found", exception.message)
    }

    @Test
    fun `Test create tag`() {
        val tag = Tag().apply {
            id = 1
            name = "Tag 1"
            color = "Color 1"
        }
        every { tagRepository.saveAndFlush(tag) } returns tag

        val createdTag = tagService.create(tag)
        assertEquals(tag.id, createdTag.id)
        assertEquals(tag.name, createdTag.name)
        assertEquals(tag.color, createdTag.color)
    }

    @Test
    fun `Test update tag`() {
        val oldTag = Tag().apply {
            id = 1
            name = "Tag 1"
            color = "Color 1"
        }
        val tag = Tag().apply {
            id = 1
            name = "Tag 2"
            color = "Color 2"
        }
        every { tagRepository.findById(tag.id!!) } returns oldTag.optional
        val analysisSlot = slot<Tag>()
        every { tagRepository.saveAndFlush(capture(analysisSlot)) } answers { analysisSlot.captured }

        val updatedTag = tagService.update(tag)

        assertEquals(tag.id, updatedTag.id)
        assertEquals(tag.name, updatedTag.name)
        assertEquals(tag.color, updatedTag.color)
    }

    @Test
    fun `Test update tag not found`() {

        val tag = Tag().apply {
            id = 1
            name = "Tag 1"
            color = "Color 1"
        }
        every { tagRepository.findById(tag.id!!) } returns Optional.empty()

        val exception = assertThrows<EntityNotFoundException> { tagService.update(tag) }
        assertEquals("[AdminConsole][Backend]: No Tag was found with id: ${tag.id}", exception.message)
    }

    @Test
    fun `Test update tag with null-attributes`() {
        val oldTag = Tag().apply {
            id = 1
            name = "Tag 1"
            color = "Color 1"
        }
        val tag = Tag().apply {
            id = 1
            name = null
            color = null
        }
        every { tagRepository.findById(tag.id!!) } returns oldTag.optional
        val analysisSlot = slot<Tag>()
        every { tagRepository.saveAndFlush(capture(analysisSlot)) } answers { analysisSlot.captured }

        val updatedTag = tagService.update(tag)

        assertEquals(oldTag.id, updatedTag.id)
        assertEquals(oldTag.name, updatedTag.name)
        assertEquals(oldTag.color, updatedTag.color)
    }
}