package com.nemethlegtechnika.products.controller

import com.nemethlegtechnika.products.dto.group.CreateGroupDto
import com.nemethlegtechnika.products.dto.group.GetGroupDto
import com.nemethlegtechnika.products.dto.group.UpdateGroupDto
import com.nemethlegtechnika.products.mapper.GroupMapper
import com.nemethlegtechnika.products.service.interfaces.GroupService
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
@RequestMapping("api/group")
class GroupController(
    private val groupMapper: GroupMapper,
    private val service: GroupService,
    private val resolver: ResponseResolver,
): BaseController() {

    @GetMapping
    fun getAll(): ResponseEntity<List<GetGroupDto>> = list(groupMapper::map) { service.getAll() } read resolver

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) = single(groupMapper::map) { service.get(id) } read resolver

    @PostMapping("/groupProducts")
    fun create(@Valid @RequestBody dto: CreateGroupDto): ResponseEntity<Unit> {
        val group = groupMapper.map(dto)
        val id = service.create(group).id!!
        return created(id)
    }

    @PutMapping
    fun update(@Valid @RequestBody dto: UpdateGroupDto): ResponseEntity<GetGroupDto> {
        val group = groupMapper.map(dto)
        return single(groupMapper::map) { service.update(group) } write resolver
    }

    @PostMapping("/{groupId}/addProduct/{productId}")
    fun addProduct(@PathVariable groupId: Long, @PathVariable productId: Long): ResponseEntity<GetGroupDto> {
        return single(groupMapper::map) { service.addProduct(productId, groupId) } write resolver
    }

    @PostMapping("/{groupId}/removeProduct/{productId}")
    fun removeProduct(@PathVariable groupId: Long, @PathVariable productId: Long): ResponseEntity<GetGroupDto> {
        return single(groupMapper::map) { service.removeProduct(productId, groupId) } write resolver
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }
}