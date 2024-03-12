package com.nemethlegtechnika.products.controller

import com.nemethlegtechnika.products.dto.tag.CreateTagDto
import com.nemethlegtechnika.products.dto.tag.GetTagDto
import com.nemethlegtechnika.products.dto.tag.UpdateTagDto
import com.nemethlegtechnika.products.mapper.TagMapper
import com.nemethlegtechnika.products.service.implementation.endpoint.TagServiceProxy
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
    private val tagService: TagServiceProxy
) : BaseController() {

    @GetMapping
    fun getAll() = ResponseEntity.ok(tagService.getAll())

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) = tagService.get(id).response()

    @PostMapping
    fun create(@Valid @RequestBody dto: CreateTagDto): ResponseEntity<Unit> {
        val tag = tagMapper.map(dto)
        val id = tagService.create(tag).id!!
        return created(id)
    }

    @PutMapping
    fun update(@Valid @RequestBody dto: UpdateTagDto): ResponseEntity<GetTagDto> {
        val tag = tagMapper.map(dto)
        return tagService.update(tag).response()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        tagService.delete(id)
        return ResponseEntity.noContent().build()
    }
}