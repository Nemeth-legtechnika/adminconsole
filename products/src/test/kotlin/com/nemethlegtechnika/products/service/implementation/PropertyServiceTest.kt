package com.nemethlegtechnika.products.service.implementation

import com.nemethlegtechnika.products.db.model.CustomProperty
import com.nemethlegtechnika.products.db.repository.CustomPropertyRepository
import com.nemethlegtechnika.products.exception.BackendException
import com.nemethlegtechnika.products.exception.EntityNotFoundException
import com.nemethlegtechnika.products.optional
import com.nemethlegtechnika.products.service.implementation.business.PropertyServiceImpl
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class PropertyServiceTest {

    private val customPropertyRepository: CustomPropertyRepository = mockk()
    private val propertyService = PropertyServiceImpl(customPropertyRepository)

    @Test
    fun `Test get all properties`() {
        every { customPropertyRepository.findAll() } returns listOf(
            CustomProperty().apply {
                id = 1
                name = "Property 1"
                value = "Value 1"
            },
            CustomProperty().apply {
                id = 2
                name = "Property 2"
                value = "Value 2"
            },
            CustomProperty().apply {
                id = 3
                name = "Property 3"
                value = "Value 3"
            },
        )

        val properties = propertyService.getAll()

        assertEquals(3, properties.size)
        assertTrue(properties.any { it.name == "Property 1" })
        assertTrue(properties.any { it.name == "Property 2" })
        assertTrue(properties.any { it.name == "Property 3" })
    }

    @Test
    fun `Test get property by name`() {
        val propertyName = "Property 1"
        every { customPropertyRepository.findByNameIgnoreCase(propertyName) } returns CustomProperty().apply {
            id = 1
            name = propertyName
            value = "Value 1"
        }.optional

        val property = propertyService.get(propertyName)

        assertEquals(propertyName, property.name)
    }

    @Test
    fun `Test get property by name not found`() {
        val propertyName = "Property 1"
        every { customPropertyRepository.findByNameIgnoreCase(propertyName) } returns Optional.empty()

        val exception = assertThrows<EntityNotFoundException> {
            propertyService.get(propertyName)
        }

        assertEquals("[AdminConsole][Backend]: No CustomProperty was found with name: $propertyName", exception.message)
    }

    @Test
    fun `Test update property`() {
        val propertyName = "Property 1"
        val oldProperty = CustomProperty().apply {
            id = 1
            name = propertyName
            value = "Value 1"
        }
        val property = CustomProperty().apply {
            id = 1
            name = propertyName
            value = "Value 2"
        }
        every { customPropertyRepository.findByNameIgnoreCase(propertyName) } returns oldProperty.optional
        val analysisSlot = slot<CustomProperty>()
        every { customPropertyRepository.saveAndFlush(capture(analysisSlot)) } answers { analysisSlot.captured }

        val result = propertyService.update(property)

        assertEquals(result.value, property.value)
    }

    @Test
    fun `Test update property with empty name`() {
        val property = CustomProperty().apply {
            id = 1
            name = ""
            value = "Value 1"
        }

        val exception = assertThrows<BackendException> {
            propertyService.update(property)
        }

        assertEquals("[AdminConsole][Backend]: Property's name cannot be empty", exception.message)
    }

    @Test
    fun `Test update property not found`() {
        val propertyName = "Property 1"
        val property = CustomProperty().apply {
            id = 1
            name = propertyName
            value = "Value 1"
        }
        every { customPropertyRepository.findByNameIgnoreCase(propertyName) } returns Optional.empty()

        val exception = assertThrows<EntityNotFoundException> {
            propertyService.update(property)
        }

        assertEquals("[AdminConsole][Backend]: No CustomProperty was found with name: $propertyName", exception.message)
    }

    @Test
    fun `Test get afa property`() {
        val propertyName = "AFA"
        every { customPropertyRepository.findByNameIgnoreCase(propertyName) } returns CustomProperty().apply {
            id = 1
            name = propertyName
            value = "27"
        }.optional

        val property = propertyService.afa

        assertEquals(propertyName, property.name)
        assertEquals("27", property.value)
    }

    @Test
    fun `Test set afa property`() {
        val propertyName = "AFA"
        val oldProperty = CustomProperty().apply {
            id = 1
            name = propertyName
            value = "27"
        }
        val property = CustomProperty().apply {
            id = 1
            name = propertyName
            value = "28"
        }
        every { customPropertyRepository.findByNameIgnoreCase(propertyName) } returns oldProperty.optional
        val analysisSlot = slot<CustomProperty>()
        every { customPropertyRepository.saveAndFlush(capture(analysisSlot)) } answers { analysisSlot.captured }

        propertyService.afa = property

        assertEquals(property.value, analysisSlot.captured.value)
    }

    @Test
    fun `Test set afa property with different name`() {
        val property = CustomProperty().apply {
            id = 1
            name = "AFA2"
            value = "28"
        }

        propertyService.afa = property

        verify { customPropertyRepository wasNot Called }
    }
}