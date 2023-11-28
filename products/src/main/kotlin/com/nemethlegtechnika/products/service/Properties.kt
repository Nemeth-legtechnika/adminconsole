package com.nemethlegtechnika.products.service

import com.nemethlegtechnika.products.db.model.CustomProperty
import com.nemethlegtechnika.products.db.repository.CustomPropertyRepository
import com.nemethlegtechnika.products.exception.AdminConsoleBackendException
import com.nemethlegtechnika.products.util.delegate.Property
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
object Properties {
    @Autowired
    private lateinit var customPropertyRepository: CustomPropertyRepository

    var AFA by Property<Double>("AFA", ::initializer, ::setter)

    private inline fun <reified T> initializer(name: String): T {
        return customPropertyRepository.findByName(name).throwWhenNotFound().value<T>()
    }

    private inline fun <reified T> setter(name: String, value: T) {
        val property = customPropertyRepository.findByName(name).throwWhenNotFound()
        property.value = "$value"
        customPropertyRepository.saveAndFlush(property)
    }

    private fun Optional<CustomProperty>.throwWhenNotFound(): CustomProperty {
        return this.orElseThrow { this.get().name?.let {
            AdminConsoleBackendException.entityNotFound<CustomProperty>("name", it)
        } }
    }
}