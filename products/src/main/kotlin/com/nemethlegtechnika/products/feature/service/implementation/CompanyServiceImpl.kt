package com.nemethlegtechnika.products.feature.service.implementation

import com.nemethlegtechnika.products.model.Company
import com.nemethlegtechnika.products.model.Company_
import com.nemethlegtechnika.products.feature.service.repository.CompanyRepository
import com.nemethlegtechnika.products.feature.service.interfaces.CompanyService
import com.nemethlegtechnika.products.feature.service.interfaces.ProductService
import com.nemethlegtechnika.products.util.findByIdOrThrow
import com.nemethlegtechnika.products.util.throwWhenNotFound
import com.nemethlegtechnika.products.util.update
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class CompanyServiceImpl(
    private val companyRepository: CompanyRepository,
    private val productService: ProductService,
) : CompanyService {
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun getAll(): List<Company> = companyRepository.findAll()

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun get(name: String): Company = companyRepository.findByNameOrThrow(name)

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun get(id: Long): Company = companyRepository.findByIdOrThrow(id)

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun create(company: Company): Company = companyRepository.saveAndFlush(company)

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun update(company: Company): Company {
        return companyRepository.update(company.id) {
            this.name = company.name ?: this.name
            this.discount = company.discount ?: this.discount
            this.margin = company.margin ?: this.margin
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun exists(name: String?): Boolean {
        return name?.let { companyRepository.existsByName(name) } ?: false
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun delete(id: Long) {
        productService.delete(companyRepository.findByIdOrThrow(id).products.map { it.id!! })
        companyRepository.deleteById(id)
    }

    private fun CompanyRepository.findByNameOrThrow(name: String): Company {
        return this.findByName(name).throwWhenNotFound(Company_.NAME, name)
    }
}