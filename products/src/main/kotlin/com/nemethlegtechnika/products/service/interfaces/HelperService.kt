package com.nemethlegtechnika.products.service.interfaces

import com.nemethlegtechnika.products.db.model.BaseEntity

interface HelperService {
    fun <F : BaseEntity, T> F.dto(mapper: (F) -> T): T
    fun <F : BaseEntity, T> List<F>.dto(mapper: (F) -> T): List<T>
}