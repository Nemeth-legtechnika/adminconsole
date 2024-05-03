package com.nemethlegtechnika.products.feature.service.interfaces

import com.nemethlegtechnika.products.model.BaseEntity

interface HelperService {
    fun <F : BaseEntity, T> F.dto(mapper: (F) -> T): T
    fun <F : BaseEntity, T> List<F>.dto(mapper: (F) -> T): List<T>
}