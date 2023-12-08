package com.nemethlegtechnika.products.controller

import com.nemethlegtechnika.products.dto.company.CreateCompanyDto
import com.nemethlegtechnika.products.dto.company.GetCompanyDto
import com.nemethlegtechnika.products.dto.company.GetCompanyProductDto
import com.nemethlegtechnika.products.dto.company.UpdateCompanyDto
import com.nemethlegtechnika.products.mapper.CompanyMapper
import com.nemethlegtechnika.products.service.implementation.endpoint.CompanyServiceProxy
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
    private val proxy: CompanyServiceProxy
): BaseController() {

    @GetMapping
    fun getAll(): ResponseEntity<List<GetCompanyDto>> = ResponseEntity.ok(proxy.getAll())

    @GetMapping("{companyName}/products")
    fun getProducts(@PathVariable companyName: String): ResponseEntity<GetCompanyProductDto> {
        return proxy.getWithProduct(companyName).response()
    }

    @GetMapping("/id/{id}")
    fun get(@PathVariable id: Long): ResponseEntity<GetCompanyDto> = proxy.get(id).response()

    @GetMapping("/{name}")
    fun get(@PathVariable name: String): ResponseEntity<GetCompanyDto> = proxy.get(name).response()

    @PostMapping
    fun create(@Valid @RequestBody dto: CreateCompanyDto): ResponseEntity<Unit> {
        val company = companyMapper.map(dto)
        val id = proxy.create(company).id!!
        return created(id)
    }

    @PutMapping
    fun update(@Valid @RequestBody dto: UpdateCompanyDto): ResponseEntity<GetCompanyDto> {
        val company = companyMapper.map(dto)
        return proxy.update(company).response()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        proxy.delete(id)
        return ResponseEntity.noContent().build<Unit>()
    }

}