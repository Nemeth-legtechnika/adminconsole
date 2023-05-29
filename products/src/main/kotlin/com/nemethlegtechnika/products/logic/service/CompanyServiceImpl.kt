package com.nemethlegtechnika.products.logic.service

import com.nemethlegtechnika.products.db.model.Company
import com.nemethlegtechnika.products.db.repository.CompanyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class CompanyServiceImpl(
    private val companyRepository: CompanyRepository
): CompanyService {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    override fun getCompany(id: Long): Company {
        return companyRepository.findById(id).orElseThrow()
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    override fun getCompanies(): List<Company> {
        return companyRepository.findAll()
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun create(company: Company): Company {
        return companyRepository.saveAndFlush(company)
    }

    override fun updateCompany(company: Company) {
        companyRepository.saveAndFlush(company)
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    override fun delete(id: Long) {
        companyRepository.deleteById(id)
    }
}