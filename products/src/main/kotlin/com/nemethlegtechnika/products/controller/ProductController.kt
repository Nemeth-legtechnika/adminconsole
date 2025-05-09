package com.nemethlegtechnika.products.controller

import com.nemethlegtechnika.products.dto.product.CreateProductDto
import com.nemethlegtechnika.products.dto.product.GetProductDto
import com.nemethlegtechnika.products.dto.product.UpdateProductDto
import com.nemethlegtechnika.products.feature.service.interfaces.ProductService
import com.nemethlegtechnika.products.feature.service.response.ResponseResolver
import com.nemethlegtechnika.products.feature.service.response.list
import com.nemethlegtechnika.products.feature.service.response.read
import com.nemethlegtechnika.products.feature.service.response.single
import com.nemethlegtechnika.products.feature.service.response.write
import com.nemethlegtechnika.products.mapper.ProductMapper
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/product")
class ProductController(
    private val productMapper: ProductMapper,
    private val service: ProductService,
    private val resolver: ResponseResolver,
) : BaseController() {

    @GetMapping
    fun getAll(@RequestParam(defaultValue = "") ids: List<Long>) = list(productMapper::map) { service.getAll(ids) } read resolver

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) = single(productMapper::map) { service.get(id) } read resolver

    @PostMapping
    fun create(@Valid @RequestBody dto: CreateProductDto): ResponseEntity<Unit> {
        val product = productMapper.map(dto)
        val id = service.create(dto.companyName!!, product).id!!
        return created(id)
    }

    @PutMapping
    fun update(@Valid @RequestBody dto: UpdateProductDto): ResponseEntity<GetProductDto> {
        val product = productMapper.map(dto)
        return single(productMapper::map) { service.update(product) } write resolver
    }

    @PostMapping("/{productId}/addTag/{tagId}")
    fun addTag(@PathVariable productId: Long, @PathVariable tagId: Long): ResponseEntity<GetProductDto> {
        return single(productMapper::map) { service.addTag(productId, tagId) } write resolver
    }

    @PostMapping("/{productId}/removeTag/{tagId}")
    fun removeTag(@PathVariable productId: Long, @PathVariable tagId: Long): ResponseEntity<GetProductDto> {
        return single(productMapper::map) { service.removeTag(productId, tagId) } write resolver
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }
}