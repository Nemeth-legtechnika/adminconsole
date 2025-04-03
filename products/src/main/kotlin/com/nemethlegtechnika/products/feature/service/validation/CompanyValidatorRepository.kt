package com.nemethlegtechnika.products.feature.service.validation

import com.nemethlegtechnika.products.exception.ConstraintViolationException
import com.nemethlegtechnika.products.feature.service.repository.CompanyRepository
import com.nemethlegtechnika.products.model.Company
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class CompanyValidatorRepository(
    @Qualifier("companyRepository") private val delegate: CompanyRepository,
): CompanyRepository by delegate, Validator<Company> {

    override fun validate(company: Company) {
        should(delegate.existsByName(company.name ?: "")) { ConstraintViolationException("Company name: ${company.name} already exists") }
    }

    override fun <S : Company> saveAndFlush(entity: S): S {
        validate(entity)
        return delegate.saveAndFlush(entity)
    }

    override fun <S : Company> save(entity: S): S {
        validate(entity)
        return delegate.save(entity)
    }

    override fun <S : Company> saveAllAndFlush(entities: MutableIterable<S>): MutableList<S> {
        entities.forEach { validate(it) }
        return delegate.saveAllAndFlush(entities)
    }

    override fun <S : Company> saveAll(entities: MutableIterable<S>): MutableList<S> {
        entities.forEach { validate(it) }
        return delegate.saveAll(entities)
    }
}