package com.nemethlegtechnika.products.service.interfaces

import com.nemethlegtechnika.products.db.model.CustomProperty

interface PropertyService {
    var afa: CustomProperty
    fun getAll(): List<CustomProperty>
    fun get(name: String): CustomProperty
    fun update(property: CustomProperty): CustomProperty
}