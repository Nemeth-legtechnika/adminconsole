package com.nemethlegtechnika.products.feature.service.response

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Component
class ResponseResolver {
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    fun <T> read(response: Response<T>): T {
        return response.value
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun <T> write(response: Response<T>): T {
        return response.value
    }
}