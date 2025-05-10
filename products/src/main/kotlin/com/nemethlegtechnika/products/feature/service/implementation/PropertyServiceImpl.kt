package com.nemethlegtechnika.products.feature.service.implementation

import com.nemethlegtechnika.common.exception.BackendException
import com.nemethlegtechnika.products.feature.constant.Properties
import com.nemethlegtechnika.products.feature.service.interfaces.PropertyService
import com.nemethlegtechnika.products.feature.service.repository.CustomPropertyRepository
import com.nemethlegtechnika.products.model.CustomProperty
import com.nemethlegtechnika.products.util.throwWhenNotFound
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class PropertyServiceImpl(
    private val customPropertyRepository: CustomPropertyRepository,
) : PropertyService {
    @get:Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    @set:Transactional(isolation = Isolation.SERIALIZABLE)
    override var afa: CustomProperty
        get() = get(Properties.AFA_NAME)
        set(value) { if (value.name == Properties.AFA_NAME) update(value) }

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun getAll(): List<CustomProperty> = customPropertyRepository.findAll()

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun get(name: String) = customPropertyRepository.findByNameIgnoreCase(name).throwWhenNotFound("name", name)

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun update(property: CustomProperty): CustomProperty {
        if (property.name.isNullOrBlank()) {
            throw BackendException("Property's name cannot be empty")
        }

        val entity = customPropertyRepository.findByNameIgnoreCase(property.name!!).throwWhenNotFound("name", property.name!!)
        entity.apply {
            this.value = property.value
        }
        return customPropertyRepository.saveAndFlush(entity)
    }
}