package com.nemethlegtechnika.products.service.implementation

import com.nemethlegtechnika.products.db.model.Company
import com.nemethlegtechnika.products.db.model.Company_
import com.nemethlegtechnika.products.db.repository.CompanyRepository
import com.nemethlegtechnika.products.service.interfaces.CompanyService
import com.nemethlegtechnika.products.service.interfaces.GroupService
import com.nemethlegtechnika.products.util.findByIdOrThrow
import com.nemethlegtechnika.products.util.throwWhenNotFound
import com.nemethlegtechnika.products.util.update
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class CompanyServiceImpl(
    private val companyRepository: CompanyRepository,
    private val groupService: GroupService,
) : CompanyService {
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun getAll(): List<Company> = companyRepository.findAll()

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun get(name: String): Company = companyRepository.findByNameOrThrow(name)

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun get(id: Long): Company = companyRepository.findByIdOrThrow(id)

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun create(company: Company): Company {
        val result = companyRepository.saveAndFlush(company)
        groupService.createDefaultGroup(result.name!!)
        return result
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun update(company: Company): Company {
        return companyRepository.update(company.id) {
            this.name = company.name ?: this.name
            this.discount = company.discount ?: this.discount
            this.margin = company.discount ?: this.discount
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun delete(id: Long) = companyRepository.deleteById(id)

    private fun CompanyRepository.findByNameOrThrow(name: String): Company {
        return this.findByName(name).throwWhenNotFound(Company_.NAME, name)
    }
}