package com.nemethlegtechnika.products.controller

import com.nemethlegtechnika.products.dto.tag.CreateTagDto
import com.nemethlegtechnika.products.dto.tag.GetTagDto
import com.nemethlegtechnika.products.dto.tag.UpdateTagDto
import com.nemethlegtechnika.products.mapper.TagMapper
import com.nemethlegtechnika.products.feature.service.interfaces.TagService
import com.nemethlegtechnika.products.feature.service.response.ResponseResolver
import com.nemethlegtechnika.products.feature.service.response.list
import com.nemethlegtechnika.products.feature.service.response.read
import com.nemethlegtechnika.products.feature.service.response.single
import com.nemethlegtechnika.products.feature.service.response.write
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
@RequestMapping("api/tag")
class TagController(
    private val tagMapper: TagMapper,
    private val service: TagService,
    private val resolver: ResponseResolver,
) : BaseController() {

    @GetMapping
    fun getAll() = list(tagMapper::map) { service.getAll() } read resolver

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) = single(tagMapper::map) { service.get(id) } read resolver

    @PostMapping
    fun create(@Valid @RequestBody dto: CreateTagDto): ResponseEntity<Unit> {
        val tag = tagMapper.map(dto)
        val id = service.create(tag).id!!
        return created(id)
    }

    @PutMapping
    fun update(@Valid @RequestBody dto: UpdateTagDto): ResponseEntity<GetTagDto> {
        val tag = tagMapper.map(dto)
        return single(tagMapper::map) { service.update(tag) } write resolver
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }
}