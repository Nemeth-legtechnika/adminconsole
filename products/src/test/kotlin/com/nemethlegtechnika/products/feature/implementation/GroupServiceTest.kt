package com.nemethlegtechnika.products.feature.implementation

import com.nemethlegtechnika.products.model.Company
import com.nemethlegtechnika.products.model.Product
import com.nemethlegtechnika.products.model.ProductGroup
import com.nemethlegtechnika.products.feature.service.repository.GroupRepository
import com.nemethlegtechnika.products.exception.EntityNotFoundException
import com.nemethlegtechnika.products.optional
import com.nemethlegtechnika.products.feature.service.interfaces.ProductService
import com.nemethlegtechnika.products.feature.service.implementation.GroupServiceImpl
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

class GroupServiceTest {

    private val productService: ProductService = mockk()
    private val groupRepository: GroupRepository = mockk()
    private val groupService = GroupServiceImpl(groupRepository, productService)

    @Test
    fun `Test get all groups`() {
        every { groupRepository.findAll() } returns listOf(
            ProductGroup().apply {
                id = 1
                name = "Group 1"
            },
            ProductGroup().apply {
                id = 2
                name = "Group 2"
            },
            ProductGroup().apply {
                id = 3
                name = "Group 3"
            },
        )

        val groups = groupService.getAll()

        assertEquals(3, groups.size)
        assertTrue(groups.any { it.name == "Group 1" })
        assertTrue(groups.any { it.name == "Group 2" })
        assertTrue(groups.any { it.name == "Group 3" })
    }

    @Test
    fun `Test get group by id successfully`() {
        val group = ProductGroup().apply {
            id = 1
            name = "Group 1"
        }
        every { groupRepository.findById(1) } returns group.optional

        val result = groupService.get(1)

        assertEquals(group.name, result.name)
    }

    @Test
    fun `Test get group by id not found`() {
        val id = 1L
        every { groupRepository.findById(id) } returns Optional.empty()

        val exception = assertThrows<EntityNotFoundException> {
            groupService.get(id)
        }

        assertEquals("[AdminConsole][Backend]: No ProductGroup was found with id: $id", exception.message)
    }

    @Test
    fun `Test create group successfully`() {
        val company = Company().apply {
            id = 1
            name = "Company 1"
        }
        val products = listOf(
            Product().apply {
                id = 1
                name = "Product 1"
                this.company = company
            },
            Product().apply {
                id = 2
                name = "Product 2"
                this.company = company
            },
            Product().apply {
                id = 3
                name = "Product 3"
                this.company = company
            },
        )
        val group = ProductGroup().apply {
            name = "Group 1"
            this.products.addAll(products)
        }

        every { productService.getAll(listOf(1L, 2L, 3L)) } returns products
        every { groupRepository.saveAndFlush(group) } returns group
        val analysisSlot = slot<List<Product>>()
        every { productService.update(capture(analysisSlot)) } answers { analysisSlot.captured }

        val result = groupService.create(group)

        assertEquals(group.name, result.name)
        assertEquals(3, result.products.size)
        assert(result.products.any { it.name == "Product 1" })
        assert(result.products.any { it.name == "Product 2" })
        assert(result.products.any { it.name == "Product 3" })
    }

    @Test
    fun `Test create group that has one product with different company's name throws exception`() {
        val company = Company().apply {
            id = 1
            name = "Company 1"
        }
        val products = listOf(
            Product().apply {
                id = 1
                name = "Product 1"
                this.company = company
            },
            Product().apply {
                id = 2
                name = "Product 2"
                this.company = Company().apply {
                    name = "Company 2"
                }
            },
            Product().apply {
                id = 3
                name = "Product 3"
                this.company = company
            },
        )
        val group = ProductGroup().apply {
            name = "Group 1"
            this.products.addAll(products)
        }

        every { productService.getAll(listOf(1L, 2L, 3L)) } returns products

        val exception = assertThrows<RuntimeException> {
            groupService.create(group)
        }

        assertEquals("[AdminConsole][Backend]: All product's company in group ${group.name} has to belong to the same company", exception.message)
    }

    @TestFactory
    fun `Test update group`() = listOf(
        ProductGroup().apply {
            id = 1
            name = "Group 2"
        },
        ProductGroup().apply {
            id = 1
            name = null
        },
    ).map { newGroup ->
        dynamicTest("Test update group with ${newGroup.name} name successfully") {
            val oldGroup = ProductGroup().apply {
                id = 1
                name = "Group 1"
            }
            every { groupRepository.findById(newGroup.id!!) } returns oldGroup.optional

            val analysisSlot = slot<ProductGroup>()
            every { groupRepository.saveAndFlush(capture(analysisSlot)) } answers { analysisSlot.captured }


            val result = groupService.update(newGroup)
            assertEquals(1L, result.id)
            newGroup.name?.let { assertEquals("Group 2", result.name) } ?: assertEquals("Group 1", result.name)
        }
    }

    @Test
    fun `Test update group not found`() {
        val group = ProductGroup().apply {
            id = 1
            name = "Group 1"
        }
        every { groupRepository.findById(group.id!!) } returns Optional.empty()

        val exception = assertThrows<EntityNotFoundException> {
            groupService.update(group)
        }

        assertEquals("[AdminConsole][Backend]: No ProductGroup was found with id: ${group.id}", exception.message)
    }

    @Test
    fun `Test add product to group successfully`() {
        val product = Product().apply {
            id = 1
            name = "Product 1"
        }
        val group = ProductGroup().apply {
            id = 1
            name = "Group 1"
        }
        every { productService.get(product.id!!) } returns product
        every { groupRepository.findById(group.id!!) } returns group.optional
        val analysisSlot = slot<ProductGroup>()
        every { groupRepository.saveAndFlush(capture(analysisSlot)) } answers { analysisSlot.captured }

        val result = groupService.addProduct(product.id!!, group.id!!)

        assertEquals(1L, result.id)
        assertEquals(1, result.products.size)
        assert(result.products.any { it.name == "Product 1" })
    }

    @Test
    fun `Test add product to group not found`() {
        val product = Product().apply {
            id = 1
            name = "Product 1"
        }
        val group = ProductGroup().apply {
            id = 1
            name = "Group 1"
        }
        every { productService.get(product.id!!) } returns product
        every { groupRepository.findById(group.id!!) } returns Optional.empty()

        val exception = assertThrows<EntityNotFoundException> {
            groupService.addProduct(product.id!!, group.id!!)
        }

        assertEquals("[AdminConsole][Backend]: No ProductGroup was found with id: ${group.id}", exception.message)
    }

    @Test
    fun `Test remove product from group successfully`() {
        val product = Product().apply {
            id = 1
            name = "Product 1"
        }
        val group = ProductGroup().apply {
            id = 1
            name = "Group 1"
            products.add(product)
        }
        every { productService.get(product.id!!) } returns product
        every { groupRepository.findById(group.id!!) } returns group.optional
        every { productService.update(product) } returns product
        val analysisSlot = slot<ProductGroup>()
        every { groupRepository.saveAndFlush(capture(analysisSlot)) } answers { analysisSlot.captured }

        val result = groupService.removeProduct(product.id!!, group.id!!)

        assertEquals(1L, result.id)
        assertEquals(0, result.products.size)
    }

    @Test
    fun `Test remove product from group not found`() {
        val product = Product().apply {
            id = 1
            name = "Product 1"
        }
        val group = ProductGroup().apply {
            id = 1
            name = "Group 1"
            products.add(product)
        }
        every { productService.get(product.id!!) } returns product
        every { groupRepository.findById(group.id!!) } returns Optional.empty()

        val exception = assertThrows<EntityNotFoundException> {
            groupService.removeProduct(product.id!!, group.id!!)
        }

        assertEquals("[AdminConsole][Backend]: No ProductGroup was found with id: ${group.id}", exception.message)
    }

    @Test
    fun `Test exists group by name`() {
        val name = "Group 1"
        every { groupRepository.existsByName(name) } returns true

        val result = groupService.exists(name)

        assertTrue(result)
    }

    @Test
    fun `Test exists group by null name`() {
        val result = groupService.exists(null)
        assertTrue(!result)
    }
}