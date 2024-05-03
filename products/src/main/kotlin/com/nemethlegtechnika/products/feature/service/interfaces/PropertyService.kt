package com.nemethlegtechnika.products.feature.service.interfaces

import com.nemethlegtechnika.products.model.CustomProperty

interface PropertyService {
    var afa: CustomProperty
    fun getAll(): List<CustomProperty>
    fun get(name: String): CustomProperty
    fun update(property: CustomProperty): CustomProperty
}