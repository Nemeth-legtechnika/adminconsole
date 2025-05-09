package com.nemethlegtechnika.products.feature.service.implementation

import com.nemethlegtechnika.common.exception.BackendException
import com.nemethlegtechnika.common.exception.EntityNotFoundException
import com.nemethlegtechnika.products.feature.service.interfaces.CompanyService
import com.nemethlegtechnika.products.feature.service.interfaces.GroupService
import com.nemethlegtechnika.products.feature.service.interfaces.ProductService
import com.nemethlegtechnika.products.feature.service.interfaces.TagService
import com.nemethlegtechnika.products.feature.service.repository.ProductRepository
import com.nemethlegtechnika.products.model.Product
import com.nemethlegtechnika.products.util.findByIdOrThrow
import com.nemethlegtechnika.products.util.update
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val tagService: TagService,
    @Lazy
    private val companyService: CompanyService,
    @Lazy
    private val groupService: GroupService,
) : ProductService {
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun getAll(companyName: String) = productRepository.findAllByCompanyName(companyName)

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun getAll(): List<Product> = productRepository.findAll()

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun getAll(productIds: List<Long>): List<Product> =
        if (productIds.isEmpty()) getAll()
        else productRepository.findAllByIdIn(productIds)

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun get(id: Long) = productRepository.findByIdOrThrow(id)

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun create(companyName: String, product: Product): Product {
        val company = companyService.get(companyName)
        product.apply {
            this.discount = this.discount ?: company.discount
            this.margin = this.margin ?: company.margin
            this.company = company
            this.group = null
        }
        return productRepository.saveAndFlush(product)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun update(product: Product): Product {
        //If we want these fields to be updated, we need to check if the company and group exists
        product.company?.let { companyService.exists(it.name).notFoundIfFalse("Company with name: ${it.name} does not exist") }
        product.group?.let { groupService.exists(it.name).notFoundIfFalse("Group with name: ${it.name} does not exist") }

        return productRepository.update(product.id) {
            this.name = product.name ?: this.name
            this.number = product.number ?: this.number
            this.description = product.description ?: this.description
            this.listPrice = product.listPrice ?: this.listPrice
            this.discount = product.discount ?: this.discount
            this.margin = product.margin ?: this.margin
            this.group = product.group ?: this.group
            this.company = product.company ?: this.company
        }
    }

    override fun update(products: List<Product>): List<Product> = productRepository.saveAllAndFlush(products)

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun addTag(productId: Long, tagId: Long): Product {
        val tag = tagService.get(tagId)
        val product = get(productId)
        product.tags.find { it.id == tagId }?.let { throw BackendException("Product with id: $productId already has tag with id: $tagId") } ?: product.tags.add(tag)
        return productRepository.saveAndFlush(product)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun removeTag(productId: Long, tagId: Long): Product {
        val product = get(productId)
        val tag = product.tags.find { it.id == tagId } ?: throw EntityNotFoundException("Product with id: $productId has no tag with id: $tagId")
        product.tags.remove(tag)
        return productRepository.saveAndFlush(product)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun delete(id: Long) {
        productRepository.deleteById(id)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun delete(ids: List<Long>) {
        productRepository.deleteAllByIdInBatch(ids)
    }

    private fun Boolean.notFoundIfFalse(message: String) {
        if (!this) throw EntityNotFoundException(message)
    }
}