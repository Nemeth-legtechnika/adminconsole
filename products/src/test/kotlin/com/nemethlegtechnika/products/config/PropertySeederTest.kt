package com.nemethlegtechnika.products.config

import com.nemethlegtechnika.products.db.model.CustomProperty
import com.nemethlegtechnika.products.db.repository.CustomPropertyRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PropertySeederTest {

    private val customPropertyRepository: CustomPropertyRepository = mockk()

    val propertySeeder = PropertySeeder(customPropertyRepository)

    @Test
    fun `Test insert afa if not exist`() {
        every { customPropertyRepository.existsByName("AFA") } returns false
        val analysisSlot = slot<CustomProperty>()
        every { customPropertyRepository.saveAndFlush(capture(analysisSlot)) } answers { analysisSlot.captured }

        propertySeeder.run()

        assertEquals("AFA", analysisSlot.captured.name)
        assertEquals("1.27", analysisSlot.captured.value)
        verify(exactly = 1) { customPropertyRepository.saveAndFlush(any()) }
    }

    @Test
    fun `Test insert afa if exist`() {
        every { customPropertyRepository.existsByName("AFA") } returns true

        propertySeeder.run()

        verify(exactly = 0) { customPropertyRepository.saveAndFlush(any()) }
    }
}