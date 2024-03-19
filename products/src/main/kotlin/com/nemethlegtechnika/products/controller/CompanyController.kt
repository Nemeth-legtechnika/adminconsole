package com.nemethlegtechnika.products.controller

import com.nemethlegtechnika.products.dto.company.CreateCompanyDto
import com.nemethlegtechnika.products.dto.company.GetCompanyDto
import com.nemethlegtechnika.products.dto.company.GetCompanyProductDto
import com.nemethlegtechnika.products.dto.company.UpdateCompanyDto
import com.nemethlegtechnika.products.mapper.CompanyMapper
import com.nemethlegtechnika.products.service.interfaces.CompanyService
import com.nemethlegtechnika.products.service.response.ResponseResolver
import com.nemethlegtechnika.products.service.response.list
import com.nemethlegtechnika.products.service.response.read
import com.nemethlegtechnika.products.service.response.single
import com.nemethlegtechnika.products.service.response.write
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/company")
class CompanyController(
    private val companyMapper: CompanyMapper,
    private val service: CompanyService,
    private val resolver: ResponseResolver,
): BaseController() {

    @GetMapping
    fun getAll() = list(companyMapper::map) { service.getAll() } read resolver

    @GetMapping("{companyName}/products")
    fun getProducts(@PathVariable companyName: String): ResponseEntity<GetCompanyProductDto> {
        return single(companyMapper::mapWithProduct) { service.get(companyName) } read resolver
    }

    @GetMapping("/id/{id}")
    fun get(@PathVariable id: Long): ResponseEntity<GetCompanyDto> = single(companyMapper::map) { service.get(id) } read resolver

    @GetMapping("/{name}")
    fun get(@PathVariable name: String): ResponseEntity<GetCompanyDto> = single(companyMapper::map) { service.get(name) } read resolver

    @PostMapping
    fun create(@Valid @RequestBody dto: CreateCompanyDto): ResponseEntity<Unit> {
        val company = companyMapper.map(dto)
        val id = service.create(company).id!!
        return created(id)
    }

    @PutMapping
    fun update(@Valid @RequestBody dto: UpdateCompanyDto): ResponseEntity<GetCompanyDto> {
        val company = companyMapper.map(dto)
        return single(companyMapper::map) { service.update(company) } write resolver
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        service.delete(id)
        return ResponseEntity.noContent().build<Unit>()
    }

}