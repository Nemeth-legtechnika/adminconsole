package com.nemethlegtechnika.products.service.implementation

import com.nemethlegtechnika.products.db.model.CustomProperty
import com.nemethlegtechnika.products.db.repository.CustomPropertyRepository
import com.nemethlegtechnika.products.exception.BackendException
import com.nemethlegtechnika.products.service.constant.Properties
import com.nemethlegtechnika.products.service.interfaces.PropertyService
import com.nemethlegtechnika.products.util.throwWhenNotFound
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class PropertyServiceImpl(private val customPropertyRepository: CustomPropertyRepository) : PropertyService {
    @get:Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    @set:Transactional(isolation = Isolation.SERIALIZABLE)
    override var afa: CustomProperty
        get() = get(Properties.AFA_NAME)
        set(value) { if (value.name == Properties.AFA_NAME) update(value) }

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun getAll(): List<CustomProperty> = customPropertyRepository.findAll()

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun get(name: String) = customPropertyRepository.findByName(name).throwWhenNotFound("name", name)

    @Transactional(isolation = Isolation.SERIALIZABLE)
    override fun update(property: CustomProperty): CustomProperty {
        if (property.name.isNullOrBlank()) {
            throw BackendException("Property's name cannot be empty")
        }

        val entity = customPropertyRepository.findByName(property.name!!).throwWhenNotFound("name", property.name!!)
        entity.apply {
            this.value = property.value
        }
        return customPropertyRepository.saveAndFlush(entity)
    }
}