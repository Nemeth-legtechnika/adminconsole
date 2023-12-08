package com.nemethlegtechnika.products.controller

import com.nemethlegtechnika.products.dto.group.CreateGroupDto
import com.nemethlegtechnika.products.dto.group.GetGroupDto
import com.nemethlegtechnika.products.dto.group.UpdateGroupDto
import com.nemethlegtechnika.products.mapper.GroupMapper
import com.nemethlegtechnika.products.service.implementation.endpoint.GroupServiceProxy
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
    private val groupService: GroupServiceProxy
): BaseController() {

    @GetMapping
    fun getAll() = ResponseEntity.ok(groupService.getAll())

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) = groupService.get(id).response()

    @PostMapping("/groupProducts")
    fun create(@Valid @RequestBody dto: CreateGroupDto): ResponseEntity<GetGroupDto> {
        val savedGroup = groupService.create(dto)
        return created(id = savedGroup.id!!, body = savedGroup)
    }

    @PutMapping
    fun update(@Valid @RequestBody dto: UpdateGroupDto): ResponseEntity<GetGroupDto> {
        return groupService.update(dto).response()
    }

    @PostMapping("/{groupId}/addProduct/{productId}")
    fun addProduct(@PathVariable groupId: Long, @PathVariable productId: Long): ResponseEntity<GetGroupDto> {
        return groupService.addProduct(productId, groupId).response()
    }

    @PostMapping("/{groupId}/removeProduct/{productId}")
    fun removeProduct(@PathVariable groupId: Long, @PathVariable productId: Long): ResponseEntity<GetGroupDto> {
        return groupService.removeProduct(productId, groupId).response()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        groupService.delete(id)
        return ResponseEntity.noContent().build()
    }
}