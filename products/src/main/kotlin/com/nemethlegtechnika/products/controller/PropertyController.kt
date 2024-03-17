package com.nemethlegtechnika.products.controller

import com.nemethlegtechnika.products.dto.property.GetPropertyDto
import com.nemethlegtechnika.products.dto.property.UpdatePropertyDto
import com.nemethlegtechnika.products.mapper.PropertyMapper
import com.nemethlegtechnika.products.service.interfaces.PropertyService
import com.nemethlegtechnika.products.service.response.ResponseResolver
import com.nemethlegtechnika.products.service.response.list
import com.nemethlegtechnika.products.service.response.read
import com.nemethlegtechnika.products.service.response.single
import com.nemethlegtechnika.products.service.response.write
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
    private val propertyMapper: PropertyMapper,
    private val service: PropertyService,
    private val resolver: ResponseResolver,
): BaseController() {

    @GetMapping
    fun getAll() = list(propertyMapper::map) { service.getAll() } read resolver

    @GetMapping("/{name}")
    fun get(@PathVariable name: String) = single(propertyMapper::map) { service.get(name) } read resolver

    @PutMapping
    fun update(@RequestBody dto: UpdatePropertyDto): ResponseEntity<GetPropertyDto> {
        val property = propertyMapper.map(dto)
        return single(propertyMapper::map) { service.update(property) } write resolver
    }
}