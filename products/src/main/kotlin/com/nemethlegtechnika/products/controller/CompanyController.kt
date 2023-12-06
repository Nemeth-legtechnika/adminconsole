package com.nemethlegtechnika.products.controller

import com.nemethlegtechnika.products.dto.company.CreateCompanyDto
import com.nemethlegtechnika.products.dto.company.GetCompanyDto
import com.nemethlegtechnika.products.dto.company.GetCompanyProductDto
import com.nemethlegtechnika.products.dto.company.UpdateCompanyDto
import com.nemethlegtechnika.products.mapper.CompanyMapper
import com.nemethlegtechnika.products.service.interfaces.CompanyService
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
    private val companyService: CompanyService,
    private val companyMapper: CompanyMapper,
): BaseController() {
    @GetMapping
    fun getAll(): ResponseEntity<List<GetCompanyDto>> =
        ResponseEntity.ok(companyService.getAll().map { companyMapper.map(it) })

    @GetMapping("{companyName}/products")
    fun getProducts(@PathVariable companyName: String): ResponseEntity<GetCompanyProductDto> {
        return companyService.get(companyName).response { companyMapper.mapWithProduct(it) }
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): ResponseEntity<GetCompanyDto> =
        companyService.get(id).response { companyMapper.map(it) }

    @GetMapping("/{name}")
    fun get(@PathVariable name: String): ResponseEntity<GetCompanyDto> =
        companyService.get(name).response { companyMapper.map(it) }

    @PostMapping
    fun create(@Valid @RequestBody dto: CreateCompanyDto): ResponseEntity<Unit> {
        val company = companyMapper.map(dto)
        val id = companyService.create(company).id!!
        return created(id)
    }

    @PutMapping
    fun update(@Valid @RequestBody dto: UpdateCompanyDto): ResponseEntity<GetCompanyDto> {
        val company = companyMapper.map(dto)
        return companyService.update(company).response { companyMapper.map(it) }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        companyService.delete(id)
        return ResponseEntity.noContent().build<Unit>()
    }

}