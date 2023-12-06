package com.nemethlegtechnika.products.controller

import com.nemethlegtechnika.products.dto.product.CreateProductDto
import com.nemethlegtechnika.products.dto.product.GetProductDto
import com.nemethlegtechnika.products.dto.product.UpdateProductDto
import com.nemethlegtechnika.products.mapper.ProductMapper
import com.nemethlegtechnika.products.service.interfaces.ProductService
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
@RequestMapping("/api/product")
class ProductController(
    private val productService: ProductService,
    private val productMapper: ProductMapper,
) : BaseController() {

    @GetMapping()
    fun getAll() = ResponseEntity.ok(productService.getAll().map { productMapper.map(it) })

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) = productService.get(id).response { productMapper.map(it) }

    @PostMapping
    fun create(@Valid @RequestBody dto: CreateProductDto): ResponseEntity<Unit> {
        val product = productMapper.map(dto)
        val id = productService.create(dto.companyName!!, product).id!!
        return created(id)
    }

    @PutMapping
    fun update(@Valid @RequestBody dto: UpdateProductDto): ResponseEntity<GetProductDto> {
        val product = productMapper.map(dto)
        return productService.update(product).response { productMapper.map(it) }
    }

    @PostMapping("/{productId}/addTag/{tagId}")
    fun addTag(@PathVariable productId: Long, @PathVariable tagId: Long): ResponseEntity<GetProductDto> {
        return productService.addTag(productId, tagId).response { productMapper.map(it) }
    }

    @PostMapping("/{productId}/removeTag/{tagId}")
    fun removeTag(@PathVariable productId: Long, @PathVariable tagId: Long): ResponseEntity<GetProductDto> {
        return productService.removeTag(productId, tagId).response { productMapper.map(it) }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        productService.delete(id)
        return ResponseEntity.noContent().build()
    }
}