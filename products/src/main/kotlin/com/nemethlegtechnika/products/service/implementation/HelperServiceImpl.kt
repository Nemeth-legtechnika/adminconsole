package com.nemethlegtechnika.products.service.implementation

import com.nemethlegtechnika.products.db.model.BaseEntity
import com.nemethlegtechnika.products.service.interfaces.HelperService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class HelperServiceImpl : HelperService {

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun <F : BaseEntity, T> F.dto(mapper: (F) -> T): T {
        return mapper(this)
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    override fun <F : BaseEntity, T> List<F>.dto(mapper: (F) -> T): List<T> {
        return this.map { mapper(it) }
    }
}