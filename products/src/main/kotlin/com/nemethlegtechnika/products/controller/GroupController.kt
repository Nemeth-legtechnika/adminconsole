package com.nemethlegtechnika.products.controller

import com.nemethlegtechnika.products.dto.group.CreateGroupDto
import com.nemethlegtechnika.products.dto.group.GetGroupDto
import com.nemethlegtechnika.products.dto.group.UpdateGroupDto
import com.nemethlegtechnika.products.mapper.GroupMapper
import com.nemethlegtechnika.products.service.interfaces.GroupService
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
    private val groupService: GroupService,
    private val groupMapper: GroupMapper
): BaseController() {

    @GetMapping
    fun getAll() = ResponseEntity.ok(groupService.getAll().map { groupMapper.map(it) })

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) = groupService.get(id).response { groupMapper.map(it) }

    @PostMapping("/groupProducts")
    fun create(@Valid @RequestBody dto: CreateGroupDto): ResponseEntity<GetGroupDto> {
        val group = groupMapper.map(dto)
        val savedGroup = groupMapper.map(groupService.create(group))
        return created(id = savedGroup.id!!, body = savedGroup)
    }

    @PutMapping
    fun update(@Valid @RequestBody dto: UpdateGroupDto): ResponseEntity<GetGroupDto> {
        val group = groupMapper.map(dto)
        return groupService.update(group).response { groupMapper.map(it) }
    }

    @PostMapping("/{groupId}/addProduct/{productId}")
    fun addProduct(@PathVariable groupId: Long, @PathVariable productId: Long): ResponseEntity<GetGroupDto> {
        return groupService.addProduct(productId, groupId).response { groupMapper.map(it) }
    }

    @PostMapping("/{groupId}/removeProduct/{productId}")
    fun removeProduct(@PathVariable groupId: Long, @PathVariable productId: Long): ResponseEntity<GetGroupDto> {
        return groupService.removeProduct(productId, groupId).response { groupMapper.map(it) }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        groupService.delete(id)
        return ResponseEntity.noContent().build()
    }
}