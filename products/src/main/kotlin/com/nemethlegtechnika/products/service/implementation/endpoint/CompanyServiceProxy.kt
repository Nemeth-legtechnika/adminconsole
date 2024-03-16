package com.nemethlegtechnika.products.service.implementation.endpoint

import com.nemethlegtechnika.products.db.model.Company
import com.nemethlegtechnika.products.dto.company.GetCompanyDto
import com.nemethlegtechnika.products.dto.company.GetCompanyProductDto
import com.nemethlegtechnika.products.mapper.CompanyMapper
import com.nemethlegtechnika.products.service.interfaces.CompanyService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service("proxy")
class CompanyServiceProxy(
    private val companyService: CompanyService,
    private val companyMapper: CompanyMapper
) {
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    fun getAll(): List<GetCompanyDto> = companyService.getAll().map { companyMapper.map(it) }

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    fun get(name: String): GetCompanyDto = companyService.get(name).let { companyMapper.map(it) }

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    fun getWithProduct(name: String): GetCompanyProductDto = companyService.get(name).let { companyMapper.mapWithProduct(it) }

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    fun get(id: Long): GetCompanyDto = companyService.get(id).let { companyMapper.map(it) }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun create(company: Company): GetCompanyDto = companyService.create(company).let { companyMapper.map(it) }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun update(company: Company): GetCompanyDto = companyService.update(company).let { companyMapper.map(it) }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun delete(id: Long) = companyService.delete(id)
}