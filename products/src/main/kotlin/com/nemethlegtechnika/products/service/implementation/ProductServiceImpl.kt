package com.nemethlegtechnika.products.service.implementation

import com.nemethlegtechnika.products.db.model.Product
import com.nemethlegtechnika.products.db.repository.ProductRepository
import com.nemethlegtechnika.products.exception.EntityNotFoundException
import com.nemethlegtechnika.products.service.interfaces.CompanyService
import com.nemethlegtechnika.products.service.interfaces.GroupService
import com.nemethlegtechnika.products.service.interfaces.ProductService
import com.nemethlegtechnika.products.service.interfaces.TagService
import com.nemethlegtechnika.products.util.findByIdOrThrow
import com.nemethlegtechnika.products.util.update
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val companyService: CompanyService,
    private val tagService: TagService,
    private val groupService: GroupService,
) : ProductService {
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun getAll(companyName: String) = productRepository.findAllByCompanyName(companyName)

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun getAll(): List<Product> = productRepository.findAll()

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun get(id: Long) = productRepository.findByIdOrThrow(id)

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun create(companyName: String, product: Product): Product {
        val company = companyService.get(companyName)
        val defaultGroup = groupService.getDefaultGroup(companyName)
        product.apply {
            this.discount = this.discount ?: company?.discount
            this.margin = this.margin ?: company?.margin
            this.company = company
            this.group = defaultGroup
        }
        return productRepository.saveAndFlush(product)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun update(product: Product): Product {
        return productRepository.update(product.id) {
            this.name = product.name ?: this.name
            this.number = product.number ?: this.number
            this.description = product.description ?: this.description
            this.listPrice = product.listPrice ?: this.listPrice
            this.discount = product.discount ?: this.discount
            this.margin = product.margin ?: this.margin
            this.group = product.group ?: this.group
            this.company = product.company ?: this.company
            if (product.tags.isNotEmpty()) {
                this.tags.apply {
                    clear()
                    addAll(product.tags)
                }
            }
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun addTag(productId: Long, tagId: Long): Product {
        val tag = tagService.get(tagId)
        val product = get(productId)
        product.tags.add(tag)
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
}