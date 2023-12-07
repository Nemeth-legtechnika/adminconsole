package com.nemethlegtechnika.products.service.implementation.endpoint

import com.nemethlegtechnika.products.db.model.CustomProperty
import com.nemethlegtechnika.products.dto.property.GetPropertyDto
import com.nemethlegtechnika.products.mapper.PropertyMapper
import com.nemethlegtechnika.products.service.interfaces.PropertyService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class PropertyServiceProxy(
    private val propertyService: PropertyService,
    private val propertyMapper: PropertyMapper,
) {
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    fun getAll(): List<GetPropertyDto> = propertyService.getAll().map { propertyMapper.map(it) }

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    fun get(name: String): GetPropertyDto = propertyService.get(name).let { propertyMapper.map(it) }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun update(property: CustomProperty): GetPropertyDto = propertyService.update(property).let { propertyMapper.map(it) }
}