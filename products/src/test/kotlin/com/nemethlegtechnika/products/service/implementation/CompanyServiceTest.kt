package com.nemethlegtechnika.products.service.implementation

import com.nemethlegtechnika.products.db.model.Company
import com.nemethlegtechnika.products.db.repository.CompanyRepository
import com.nemethlegtechnika.products.exception.EntityNotFoundException
import com.nemethlegtechnika.products.optional
import com.nemethlegtechnika.products.service.interfaces.GroupService
import com.nemethlegtechnika.products.service.interfaces.ProductService
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertThrows
import java.util.*

class CompanyServiceTest {

    private val groupService: GroupService = mockk()

    private val companyRepository: CompanyRepository = mockk()

    private val productService: ProductService = mockk()

    private val companyService = CompanyServiceImpl(companyRepository, productService)

    @Test
    fun `Test get all companies`() {
        every { companyRepository.findAll() } returns listOf(
            Company().apply {
                id = 1
                name = "Company 1"
                discount = 10.0
                margin = 20.0
            },
            Company().apply {
                id = 2
                name = "Company 2"
                discount = 15.0
                margin = 25.0
            },
            Company().apply {
                id = 3
                name = "Company 3"
                discount = 20.0
                margin = 30.0
            },
        )

        val companies = companyService.getAll()

        assertTrue(companies.size == 3)
        assertTrue(companies.any { it.name == "Company 1" })
        assertTrue(companies.any { it.name == "Company 2" })
        assertTrue(companies.any { it.name == "Company 3" })
    }

    @Test
    fun `Test get company by name successfully`() {
        val companyName = "Company 1"
        every { companyRepository.findByName(companyName) } returns Company().apply {
            id = 1
            name = companyName
            discount = 10.0
            margin = 20.0
        }.optional

        val company = companyService.get(companyName)

        assertTrue(company.name == companyName)
    }

    @Test
    fun `Test get company by name not found`() {
        val companyName = "Company 1"
        every { companyRepository.findByName(companyName) } returns Optional.empty()

        val exception = assertThrows<EntityNotFoundException> {
            companyService.get(companyName)
        }

        assertEquals("[AdminConsole][Backend]: No Company was found with name: $companyName", exception.message)
    }

    @Test
    fun `Test get company by id successfully`() {
        val companyId = 1L
        every { companyRepository.findById(companyId) } returns Company().apply {
            id = companyId
            name = "Company 1"
            discount = 10.0
            margin = 20.0
        }.optional

        val company = companyService.get(companyId)

        assertTrue(company.id == companyId)
    }

    @Test
    fun `Test get company by id not found`() {
        val companyId = 1L
        every { companyRepository.findById(companyId) } returns Optional.empty()

        val exception = assertThrows<EntityNotFoundException> {
            companyService.get(companyId)
        }

        assertEquals("[AdminConsole][Backend]: No Company was found with id: $companyId", exception.message)
    }

    @Test
    fun `Test create company successfully`() {
        val company = Company().apply {
            id = 1
            name = "Company 1"
            discount = 10.0
            margin = 20.0
        }

        every { companyRepository.saveAndFlush(company) } returns company

        val result = companyService.create(company)

        assertEquals( 1L, result.id)
        assertEquals("Company 1", result.name)
        assertEquals( 10.0, result.discount)
        assertEquals( 20.0, result.margin)
    }

    @TestFactory
    fun `Test update company`() = mapOf(
        Company().apply {
            id = 1
            name = "Company 2"
            discount = 15.0
            margin = 30.0
        } to "-",
        Company().apply {
            id = 1
            name = "Company 2"
            discount = 15.0
            margin = null
        } to "margin",
        Company().apply {
            id = 1
            name = null
            discount = null
            margin = 30.0
        } to "name, discount"
    ).map { (newCompany, message) ->
        dynamicTest("Test update company with null-attributes: [$message] successfully") {
            val oldCompany = Company().apply {
                id = 1
                name = "Company 1"
                discount = 10.0
                margin = 20.0
            }

            every { companyRepository.findById(newCompany.id!!) } returns oldCompany.optional

            val analysisSlot = slot<Company>()
            every { companyRepository.saveAndFlush(capture(analysisSlot)) } answers { analysisSlot.captured }

            val result = companyService.update(newCompany)

            assertEquals( 1L, result.id)
            newCompany.name?.let { assertEquals("Company 2", result.name) } ?: assertEquals("Company 1", result.name)
            newCompany.discount?.let { assertEquals( 15.0, result.discount) } ?: assertEquals( 10.0, result.discount)
            newCompany.margin?.let { assertEquals( 30.0, result.margin) } ?: assertEquals( 20.0, result.margin)
        }
    }

    @Test
    fun `Test update company not found`() {
        val company = Company().apply {
            id = 1
            name = "Company 1"
            discount = 10.0
            margin = 20.0
        }
        every { companyRepository.findById(company.id!!) } returns Optional.empty()

        val exception = assertThrows<EntityNotFoundException> {
            companyService.update(company)
        }

        assertEquals("[AdminConsole][Backend]: No Company was found with id: ${company.id}", exception.message)
    }

    @Test
    fun `Test exists company by name`() {
        val companyName = "Company 1"
        every { companyRepository.existsByName(companyName) } returns true

        val result = companyService.exists(companyName)

        assertTrue(result)
    }

    @Test
    fun `Test exists company by null name`() {
        val result = companyService.exists(null)
        assertTrue(!result)
    }
}