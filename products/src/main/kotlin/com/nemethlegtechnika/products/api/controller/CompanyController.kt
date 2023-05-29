package com.nemethlegtechnika.products.api.controller

import com.nemethlegtechnika.products.api.dto.CompanyDto
import com.nemethlegtechnika.products.logic.map.CompanyMapper
import com.nemethlegtechnika.products.logic.service.CompanyService
import kotlinx.coroutines.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/company")
class CompanyController(
    private val companyService: CompanyService
) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val mapper = CompanyMapper.INSTANCE

    @GetMapping
    fun gatAll(): ResponseEntity<List<CompanyDto>> = runBlocking {
        val result = companyService.getCompanies().map {
            async { mapper.map(it) }
        }
        ResponseEntity.ok(result.awaitAll())
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<CompanyDto> {
        return ResponseEntity.ok(mapper.map(companyService.getCompany(id)))
    }

    @PostMapping
    fun createCompany(@RequestBody request: CompanyDto): ResponseEntity<CompanyDto> {
        val result = companyService.create(mapper.map(request))
        return ResponseEntity.ok(mapper.map(result))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        companyService.delete(id)
        return ResponseEntity.noContent().build()
    }
}