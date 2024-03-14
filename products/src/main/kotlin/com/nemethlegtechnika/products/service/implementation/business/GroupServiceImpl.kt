package com.nemethlegtechnika.products.service.implementation.business

import com.nemethlegtechnika.products.db.model.Company
import com.nemethlegtechnika.products.db.model.ProductGroup
import com.nemethlegtechnika.products.db.repository.GroupRepository
import com.nemethlegtechnika.products.exception.BackendException
import com.nemethlegtechnika.products.service.interfaces.GroupService
import com.nemethlegtechnika.products.service.interfaces.ProductService
import com.nemethlegtechnika.products.util.findByIdOrThrow
import com.nemethlegtechnika.products.util.update
import com.nemethlegtechnika.products.util.value
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class GroupServiceImpl(
    private val groupRepository: GroupRepository,
    private val productService: ProductService,
) : GroupService{

    companion object {
        val logger = KotlinLogging.logger {  }
        const val DEFAULT_NAME = "Default"
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun getAll(): List<ProductGroup> = groupRepository.findAll()

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun get(id: Long) = groupRepository.findByIdOrThrow(id)

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun getDefaultGroup(company: Company): ProductGroup {
        if (company.id == null && company.name.isNullOrBlank()) {
            throw BackendException("Company's id or name should not be null when searching for default group")
        }
        val defaultGroup = if (company.id == null) {
            groupRepository.findDefaultGroup(company.name!!).value
        } else {
            groupRepository.findDefaultGroup(company.id!!).value
        }
        if (defaultGroup == null) {
            logger.debug { "Default group could not be found for company: ${company.name}" }
            return createDefaultGroup(company)
        }
        return defaultGroup
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun createDefaultGroup(company: Company): ProductGroup {
        val defaultGroup = ProductGroup().apply {
            this.name = DEFAULT_NAME
            this.company = company
        }
        val result = groupRepository.saveAndFlush(defaultGroup)
        logger.debug { "New DefaultGroup was created" }
        return result
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun create(group: ProductGroup): ProductGroup {
        val ids = group.products.map { it.id!! }
        val products = productService.getAll(ids)
        if(!products.all { it.company?.name == group.company?.name }) {
            throw BackendException("All product's company in group ${group.name} has to be ${group.company?.name}")
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
            checkDefaultGroup(group.id!!)
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
        checkDefaultGroup(groupId)
        group.products.remove(product)
        product.group = product.company?.let { getDefaultGroup(it) }
        productService.update(product)
        return groupRepository.saveAndFlush(group)
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun exists(name: String?): Boolean {
        return name?.let { groupRepository.existsByName(name) } ?: false
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun delete(id: Long) {
        checkDefaultGroup(id)
        groupRepository.deleteById(id)
    }

    private fun checkDefaultGroup(groupId: Long) {
        if (groupRepository.isDefaultGroup(groupId)) {
            throw BackendException("Default group with id: $groupId cannot be modified or deleted")
        }
    }
}