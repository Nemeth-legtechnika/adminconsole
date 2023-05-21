package com.nemethlegtechnika.products.logic.service

import com.nemethlegtechnika.products.db.repository.CompanyRepository
import org.springframework.stereotype.Service

@Service
class CompanyServiceImpl(
    private val companyRepository: CompanyRepository
): CompanyService {

}