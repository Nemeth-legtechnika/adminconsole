package com.nemethlegtechnika.products.util

import com.nemethlegtechnika.products.db.model.BaseEntity
import com.nemethlegtechnika.products.exception.EntityNotFoundException
import com.nemethlegtechnika.products.optional
import io.mockk.InternalPlatformDsl.toStr
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

class RepositoryUtilTest {

    @Test
    fun `Test throwWhenNotFound`() {
        val optional = Optional.empty<BaseEntity>()

        val exception = assertThrows<EntityNotFoundException> { optional.throwWhenNotFound(1) }

        assertEquals("[AdminConsole][Backend]: No BaseEntity was found with id: 1", exception.message)
    }

    @Test
    fun `Test throwWhenNotFound with attribute`() {
        val optional = Optional.empty<BaseEntity>()

        val exception = assertThrows<EntityNotFoundException> { optional.throwWhenNotFound("name", "value") }

        assertEquals("[AdminConsole][Backend]: No BaseEntity was found with name: value", exception.message)
    }

    @Test
    fun `Test findByIdOrThrow`() {
        val repository: JpaRepository<BaseEntity, Long> = mockk()

        every { repository.findById(1L) } returns Optional.empty()

        val exception = assertThrows<EntityNotFoundException> { repository.findByIdOrThrow(1) }

        assertEquals("[AdminConsole][Backend]: No BaseEntity was found with id: 1", exception.message)
    }

    @Test
    fun `Test update`() {
        val repository: JpaRepository<BaseEntity, Long> = mockk()
        val result: BaseEntity = mockk()

        every { repository.findById(1L) } returns result.optional
        val analysisSlot = slot<BaseEntity>()
        every { repository.saveAndFlush(capture(analysisSlot)) } answers { analysisSlot.captured }

        repository.update(1) {
            result.toStr()
        }

        verify(exactly = 1) { result.toStr() }
    }
}