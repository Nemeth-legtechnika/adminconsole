package com.nemethlegtechnika.products.service.implementation.endpoint

import com.nemethlegtechnika.products.db.model.Company
import com.nemethlegtechnika.products.dto.company.GetCompanyDto
import com.nemethlegtechnika.products.dto.company.GetCompanyProductDto
import com.nemethlegtechnika.products.mapper.CompanyMapper
import com.nemethlegtechnika.products.service.implementation.business.CompanyServiceImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service("proxy")
class CompanyServiceProxy(
    private val companyServiceImpl: CompanyServiceImpl,
    private val companyMapper: CompanyMapper
) {
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    fun getAll(): List<GetCompanyDto> = companyServiceImpl.getAll().map { companyMapper.map(it) }

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    fun get(name: String): GetCompanyDto = companyServiceImpl.get(name).let { companyMapper.map(it) }

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    fun getWithProduct(name: String): GetCompanyProductDto = companyServiceImpl.get(name).let { companyMapper.mapWithProduct(it) }

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    fun get(id: Long): GetCompanyDto = companyServiceImpl.get(id).let { companyMapper.map(it) }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun create(company: Company): GetCompanyDto = companyServiceImpl.create(company).let { companyMapper.map(it) }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun update(company: Company): GetCompanyDto = companyServiceImpl.update(company).let { companyMapper.map(it) }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun delete(id: Long) = companyServiceImpl.delete(id)
}