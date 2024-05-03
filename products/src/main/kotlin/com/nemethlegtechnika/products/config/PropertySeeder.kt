package com.nemethlegtechnika.products.config

import com.nemethlegtechnika.products.model.CustomProperty
import com.nemethlegtechnika.products.model.Type
import com.nemethlegtechnika.products.feature.service.repository.CustomPropertyRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class PropertySeeder(private val customPropertyRepository: CustomPropertyRepository) : CommandLineRunner {
    override fun run(vararg args: String?) {
        insertAfa()
    }

    private fun insertAfa() {
        val afa = CustomProperty().apply {
            name = "AFA"
            value = "1.27"
            type = Type.DOUBLE
        }
        insertIfNotExist(afa)
    }

    private fun insertIfNotExist(property: CustomProperty) {
        property.name?.let {
            if (!customPropertyRepository.existsByName(it)) {
                customPropertyRepository.saveAndFlush(property)
            }
        }
    }
}