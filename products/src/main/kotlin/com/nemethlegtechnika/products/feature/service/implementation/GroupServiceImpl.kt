package com.nemethlegtechnika.products.feature.service.implementation

import com.nemethlegtechnika.common.exception.BackendException
import com.nemethlegtechnika.products.feature.service.interfaces.GroupService
import com.nemethlegtechnika.products.feature.service.interfaces.ProductService
import com.nemethlegtechnika.products.feature.service.repository.GroupRepository
import com.nemethlegtechnika.products.model.ProductGroup
import com.nemethlegtechnika.products.util.findByIdOrThrow
import com.nemethlegtechnika.products.util.update
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class GroupServiceImpl(
    private val groupRepository: GroupRepository,
    private val productService: ProductService,
) : GroupService {

    companion object {
        val logger = KotlinLogging.logger {  }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun getAll(): List<ProductGroup> = groupRepository.findAll()

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun get(id: Long) = groupRepository.findByIdOrThrow(id)

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun create(group: ProductGroup): ProductGroup {
        val ids = group.products.map { it.id!! }
        val products = productService.getAll(ids)
        if(!products.all { it.company?.name == products.first().company?.name }) {
            throw BackendException("All product's company in group ${group.name} has to belong to the same company")
        }

        products.forEach {
            it.group = group
        }

        val result = groupRepository.saveAndFlush(group)
        productService.update(products)
        return result
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun update(group: ProductGroup): ProductGroup {
        return groupRepository.update(group.id) {
            this.name = group.name ?: this.name
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun addProduct(productId: Long, groupId: Long): ProductGroup {
        val product = productService.get(productId)
        val group = get(groupId)
        group.products.add(product)
        return groupRepository.saveAndFlush(group)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun removeProduct(productId: Long, groupId: Long): ProductGroup {
        val product = productService.get(productId)
        val group = get(groupId)
        group.products.remove(product)
        product.group = null
        productService.update(product)
        return groupRepository.saveAndFlush(group)
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun exists(name: String?): Boolean {
        return name?.let { groupRepository.existsByName(name) } ?: false
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun delete(id: Long) {
        get(id).products.forEach {
            removeProduct(it.id!!, id)
        }
        groupRepository.deleteById(id)
    }
}