package com.nemethlegtechnika.products.controller

import com.nemethlegtechnika.products.dto.property.GetPropertyDto
import com.nemethlegtechnika.products.dto.property.UpdatePropertyDto
import com.nemethlegtechnika.products.mapper.PropertyMapper
import com.nemethlegtechnika.products.service.interfaces.PropertyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/property")
class PropertyController(
    private val propertyService: PropertyService,
    private val propertyMapper: PropertyMapper
): BaseController() {

    @GetMapping
    fun getAll() = ResponseEntity.ok(propertyService.getAll().map { propertyMapper.map(it) })

    @GetMapping("/{name}")
    fun get(@PathVariable name: String) = propertyService.get(name).response { propertyMapper.map(it) }

    @PutMapping
    fun update(@RequestBody dto: UpdatePropertyDto): ResponseEntity<GetPropertyDto> {
        val property = propertyMapper.map(dto)
        return propertyService.update(property).response { propertyMapper.map(it) }
    }
}