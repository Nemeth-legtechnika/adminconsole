package com.nemethlegtechnika.products.service.implementation

import com.nemethlegtechnika.products.db.model.Company
import com.nemethlegtechnika.products.db.model.Product
import com.nemethlegtechnika.products.db.model.ProductGroup
import com.nemethlegtechnika.products.db.model.Tag
import com.nemethlegtechnika.products.db.repository.ProductRepository
import com.nemethlegtechnika.products.exception.EntityNotFoundException
import com.nemethlegtechnika.products.optional
import com.nemethlegtechnika.products.service.interfaces.CompanyService
import com.nemethlegtechnika.products.service.interfaces.GroupService
import com.nemethlegtechnika.products.service.interfaces.TagService
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

class ProductServiceTest {

    private val productRepository: ProductRepository = mockk()
    private val tagService: TagService = mockk()
    private val companyService: CompanyService = mockk()
    private val groupService: GroupService = mockk()
    private val productService = ProductServiceImpl(productRepository, tagService, companyService, groupService)

    @Test
    fun `Test get all products`() {
        every { productRepository.findAll() } returns listOf(
            Product().apply {
                id = 1
                name = "Product 1"
                number = "123"
                description = "Description 1"
                listPrice = 100L
                discount = 10.0
                margin = 20.0
            },
            Product().apply {
                id = 2
                name = "Product 2"
                number = "456"
                description = "Description 2"
                listPrice = 200L
                discount = 15.0
                margin = 25.0
            },
            Product().apply {
                id = 3
                name = "Product 3"
                number = "789"
                description = "Description 3"
                listPrice = 300L
                discount = 20.0
                margin = 30.0
            },
        )

        val products = productService.getAll()

        assertEquals(3, products.size)
        assertTrue(products.any { it.name == "Product 1" })
        assertTrue(products.any { it.name == "Product 2" })
        assertTrue(products.any { it.name == "Product 3" })
    }

    @Test
    fun `Test get all products by company name`() {
        val companyName = "Company 1"
        every { productRepository.findAllByCompanyName(companyName) } returns listOf(
            Product().apply {
                id = 1
                name = "Product 1"
                number = "123"
                description = "Description 1"
                listPrice = 100L
                discount = 10.0
                margin = 20.0
            },
            Product().apply {
                id = 2
                name = "Product 2"
                number = "456"
                description = "Description 2"
                listPrice = 200L
                discount = 15.0
                margin = 25.0
            },
            Product().apply {
                id = 3
                name = "Product 3"
                number = "789"
                description = "Description 3"
                listPrice = 300L
                discount = 20.0
                margin = 30.0
            },
        )

        val products = productService.getAll(companyName)

        assertEquals(3, products.size)
        assertTrue(products.any { it.name == "Product 1" })
        assertTrue(products.any { it.name == "Product 2" })
        assertTrue(products.any { it.name == "Product 3" })
    }

    @Test
    fun `Test get all products by ids`() {
        val productIds = listOf(1L, 2L, 3L)
        every { productRepository.findAllByIdIn(productIds) } returns listOf(
            Product().apply {
                id = 1
                name = "Product 1"
                number = "123"
                description = "Description 1"
                listPrice = 100L
                discount = 10.0
                margin = 20.0
            },
            Product().apply {
                id = 2
                name = "Product 2"
                number = "456"
                description = "Description 2"
                listPrice = 200L
                discount = 15.0
                margin = 25.0
            },
            Product().apply {
                id = 3
                name = "Product 3"
                number = "789"
                description = "Description 3"
                listPrice = 300L
                discount = 20.0
                margin = 30.0
            },
        )

        val products = productService.getAll(productIds)

        assertEquals(3, products.size)
        assertTrue(products.any { it.name == "Product 1" })
        assertTrue(products.any { it.name == "Product 2" })
        assertTrue(products.any { it.name == "Product 3" })
    }

    @Test
    fun `Test get product by id successfully`() {
        val productId = 1L
        every { productRepository.findById(productId) } returns Product().apply {
            id = productId
            name = "Product 1"
            number = "123"
            description = "Description 1"
            listPrice = 100L
            discount = 10.0
            margin = 20.0
        }.optional

        val product = productService.get(productId)

        assertEquals(productId, product.id)
        assertEquals("Product 1", product.name)
        assertEquals("123", product.number)
        assertEquals("Description 1", product.description)
        assertEquals(100L, product.listPrice)
        assertEquals(10.0, product.discount)
        assertEquals(20.0, product.margin)
    }

    @Test
    fun `Test get product by id not found`() {
        val productId = 1L
        every { productRepository.findById(productId) } returns Optional.empty()

        val exception = assertThrows<EntityNotFoundException> {
            productService.get(productId)
        }

        assertEquals("[AdminConsole][Backend]: No Product was found with id: $productId", exception.message)
    }

    @Test
    fun `Test create product successfully`() {
        val companyName = "Company 1"
        val product = Product().apply {
            name = "Product 1"
            number = "123"
            description = "Description 1"
            listPrice = 100L
            discount = 10.0
            margin = 20.0
        }
        every { companyService.get(companyName) } returns Company().apply {
            id = 1
            name = companyName
            discount = 10.0
            margin = 20.0
        }
        every { productRepository.saveAndFlush(product) } returns product

        val result = productService.create(companyName, product)

        assertEquals("Product 1", result.name)
        assertEquals("123", result.number)
        assertEquals("Description 1", result.description)
        assertEquals(100L, result.listPrice)
        assertEquals(10.0, result.discount)
        assertEquals(20.0, result.margin)
        assertEquals("Company 1", result.company?.name)
        assertEquals(null, result.group)
    }

    @Test
    fun `Test create product with default discount and margin successfully`() {
        val companyName = "Company 1"
        val product = Product().apply {
            name = "Product 1"
            number = "123"
            description = "Description 1"
            listPrice = 100L
        }
        every { companyService.get(companyName) } returns Company().apply {
            id = 1
            name = companyName
            discount = 10.0
            margin = 20.0
        }
        every { productRepository.saveAndFlush(product) } returns product

        val result = productService.create(companyName, product)

        assertEquals("Product 1", result.name)
        assertEquals("123", result.number)
        assertEquals("Description 1", result.description)
        assertEquals(100L, result.listPrice)
        assertEquals(10.0, result.discount)
        assertEquals(20.0, result.margin)
        assertEquals("Company 1", result.company?.name)
        assertEquals(null, result.group)
    }

    @TestFactory
    fun `Test update product`() = mapOf(
        Product().apply {
            id = 1
            name = "Product 2"
            number = "456"
            description = "Description 2"
            listPrice = 200L
            discount = 15.0
            margin = 25.0
        } to "-",
        Product().apply {
            id = 1
            name = "Product 2"
            number = "456"
            description = "Description 2"
            listPrice = null
            discount = null
            margin = null
        } to "margin, discount, listPrice",
        Product().apply {
            id = 1
            name = null
            number = null
            description = null
            listPrice = 200L
            discount = 15.0
            margin = 25.0
        } to "name, number, description",
        Product().apply {
            id = 1
            name = "Product 2"
            number = null
            description = null
            listPrice = null
            discount = null
            margin = 25.0
        } to "number",
    ).map { (newProduct, attributes) ->
        dynamicTest("Test update product with null-attributes: [$attributes] successfully") {
            val oldProduct = Product().apply {
                id = 1
                name = "Product 1"
                number = "123"
                description = "Description 1"
                listPrice = 100L
                discount = 10.0
                margin = 20.0
            }

            every { productRepository.findById(newProduct.id!!) } returns oldProduct.optional

            val analysisSlot = slot<Product>()
            every { productRepository.saveAndFlush(capture(analysisSlot)) } answers { analysisSlot.captured }

            val result = productService.update(newProduct)

            assertEquals(1L, result.id)
            newProduct.name?.let { assertEquals("Product 2", result.name) } ?: assertEquals("Product 1", result.name)
            newProduct.number?.let { assertEquals("456", result.number) } ?: assertEquals("123", result.number)
            newProduct.description?.let { assertEquals("Description 2", result.description) } ?: assertEquals("Description 1", result.description)
            newProduct.listPrice?.let { assertEquals(200L, result.listPrice) } ?: assertEquals(100L, result.listPrice)
            newProduct.discount?.let { assertEquals(15.0, result.discount) } ?: assertEquals(10.0, result.discount)
            newProduct.margin?.let { assertEquals(25.0, result.margin) } ?: assertEquals(20.0, result.margin)
        }
    }

    @Test
    fun `Test update product with company and group successfully`() {
        val newProduct = Product().apply {
            id = 1
            name = "Product 2"
            number = "456"
            description = "Description 2"
            listPrice = 200L
            discount = 15.0
            margin = 25.0
            company = Company().apply {
                id = 1
                name = "Company 1"
                discount = 10.0
                margin = 20.0
            }
            group = ProductGroup().apply {
                id = 1
                name = "Group 1"
            }
        }
        val oldProduct = Product().apply {
            id = 1
            name = "Product 1"
            number = "123"
            description = "Description 1"
            listPrice = 100L
            discount = 10.0
            margin = 20.0
            company = Company().apply {
                id = 1
                name = "Company 1"
                discount = 10.0
                margin = 20.0
            }
            group = ProductGroup().apply {
                id = 1
                name = "Group 1"
            }
        }

        every { productRepository.findById(newProduct.id!!) } returns oldProduct.optional
        every { companyService.exists(newProduct.company!!.name) } returns true
        every { groupService.exists(newProduct.group!!.name) } returns true
        val analysisSlot = slot<Product>()
        every { productRepository.saveAndFlush(capture(analysisSlot)) } answers { analysisSlot.captured }

        val result = productService.update(newProduct)

        assertEquals(1L, result.id)
        assertEquals("Product 2", result.name)
        assertEquals("456", result.number)
        assertEquals("Description 2", result.description)
        assertEquals(200L, result.listPrice)
        assertEquals(15.0, result.discount)
        assertEquals(25.0, result.margin)
        assertEquals("Company 1", result.company?.name)
        assertEquals("Group 1", result.group?.name)
    }

    @Test
    fun `Test update product with company not found`() {
        val newProduct = Product().apply {
            id = 1
            name = "Product 2"
            number = "456"
            description = "Description 2"
            listPrice = 200L
            discount = 15.0
            margin = 25.0
            company = Company().apply {
                id = 1
                name = "Company 1"
                discount = 10.0
                margin = 20.0
            }
            group = ProductGroup().apply {
                id = 1
                name = "Group 1"
            }
        }
        val oldProduct = Product().apply {
            id = 1
            name = "Product 1"
            number = "123"
            description = "Description 1"
            listPrice = 100L
            discount = 10.0
            margin = 20.0
            company = Company().apply {
                id = 1
                name = "Company 1"
                discount = 10.0
                margin = 20.0
            }
            group = ProductGroup().apply {
                id = 1
                name = "Group 1"
            }
        }

        every { productRepository.findById(newProduct.id!!) } returns oldProduct.optional
        every { companyService.exists(newProduct.company!!.name) } returns false

        val exception = assertThrows<EntityNotFoundException> {
            productService.update(newProduct)
        }

        assertEquals("[AdminConsole][Backend]: Company with name: ${newProduct.company!!.name} does not exist", exception.message)
    }

    @Test
    fun `Test update product with group not found`() {
        val newProduct = Product().apply {
            id = 1
            name = "Product 2"
            number = "456"
            description = "Description 2"
            listPrice = 200L
            discount = 15.0
            margin = 25.0
            company = Company().apply {
                id = 1
                name = "Company 1"
                discount = 10.0
                margin = 20.0
            }
            group = ProductGroup().apply {
                id = 1
                name = "Group 1"
            }
        }
        val oldProduct = Product().apply {
            id = 1
            name = "Product 1"
            number = "123"
            description = "Description 1"
            listPrice = 100L
            discount = 10.0
            margin = 20.0
            company = Company().apply {
                id = 1
                name = "Company 1"
                discount = 10.0
                margin = 20.0
            }
            group = ProductGroup().apply {
                id = 1
                name = "Group 1"
            }
        }

        every { productRepository.findById(newProduct.id!!) } returns oldProduct.optional
        every { companyService.exists(newProduct.company!!.name) } returns true
        every { groupService.exists(newProduct.group!!.name) } returns false

        val exception = assertThrows<EntityNotFoundException> {
            productService.update(newProduct)
        }

        assertEquals("[AdminConsole][Backend]: Group with name: ${newProduct.group!!.name} does not exist", exception.message)
    }

    @Test
    fun `Test add tag to product successfully`() {
        val productId = 1L
        val tagId = 1L
        val product = Product().apply {
            id = productId
            name = "Product 1"
            number = "123"
            description = "Description 1"
            listPrice = 100L
            discount = 10.0
            margin = 20.0
        }
        val tag = Tag().apply {
            id = tagId
            name = "Tag 1"
        }

        every { tagService.get(tagId) } returns tag
        every { productRepository.findById(productId) } returns product.optional
        val analysisSlot = slot<Product>()
        every { productRepository.saveAndFlush(capture(analysisSlot)) } answers { analysisSlot.captured }

        val result = productService.addTag(productId, tagId)

        assertEquals(productId, result.id)
        assertTrue(result.tags.any { it.id == tagId })
    }

    @Test
    fun `Test add tag to product that already has`() {
        val productId = 1L
        val tagId = 1L
        val product = Product().apply {
            id = productId
            name = "Product 1"
            number = "123"
            description = "Description 1"
            listPrice = 100L
            discount = 10.0
            margin = 20.0
            tags = mutableListOf(
                Tag().apply {
                    id = tagId
                    name = "Tag 1"
                }
            )
        }
        val tag = Tag().apply {
            id = tagId
            name = "Tag 1"
        }

        every { tagService.get(tagId) } returns tag
        every { productRepository.findById(productId) } returns product.optional

        val exception = assertThrows<EntityNotFoundException> {
            productService.addTag(productId, tagId)
        }

        assertEquals("[AdminConsole][Backend]: Product with id: $productId already has tag with id: $tagId", exception.message)
    }

    @Test
    fun `Test add tag to product not found`() {
        val productId = 1L
        val tagId = 1L
        val tag = Tag().apply {
            id = tagId
            name = "Tag 1"
        }

        every { productRepository.findById(productId) } returns Optional.empty()
        every { tagService.get(tagId) } returns tag

        val exception = assertThrows<EntityNotFoundException> {
            productService.addTag(productId, tagId)
        }

        assertEquals("[AdminConsole][Backend]: No Product was found with id: $productId", exception.message)
    }

    @Test
    fun `Test remove tag from product successfully`() {
        val productId = 1L
        val tagId = 1L
        val product = Product().apply {
            id = productId
            name = "Product 1"
            number = "123"
            description = "Description 1"
            listPrice = 100L
            discount = 10.0
            margin = 20.0
            tags = mutableListOf(
                Tag().apply {
                    id = tagId
                    name = "Tag 1"
                }
            )
        }

        every { productRepository.findById(productId) } returns product.optional
        val analysisSlot = slot<Product>()
        every { productRepository.saveAndFlush(capture(analysisSlot)) } answers { analysisSlot.captured }

        val result = productService.removeTag(productId, tagId)

        assertEquals(productId, result.id)
        assertTrue(result.tags.none { it.id == tagId })
    }


    @Test
    fun `Test remove tag from product not found`() {
        val productId = 1L
        val tagId = 1L

        val tag = Tag().apply {
            id = tagId
            name = "Tag 1"
        }

        every { productRepository.findById(productId) } returns Optional.empty()
        every { tagService.get(tagId) } returns tag

        val exception = assertThrows<EntityNotFoundException> {
            productService.removeTag(productId, tagId)
        }

        assertEquals("[AdminConsole][Backend]: No Product was found with id: $productId", exception.message)
    }

    @Test
    fun `Test remove tag that product does not have`() {
        val productId = 1L
        val tagId = 1L
        val product = Product().apply {
            id = productId
            name = "Product 1"
            number = "123"
            description = "Description 1"
            listPrice = 100L
            discount = 10.0
            margin = 20.0
            tags = mutableListOf()
        }

        every { productRepository.findById(productId) } returns product.optional

        val exception = assertThrows<EntityNotFoundException> {
            productService.removeTag(productId, tagId)
        }

        assertEquals("[AdminConsole][Backend]: Product with id: $productId has no tag with id: $tagId", exception.message)
    }
}