package com.nemethlegtechnika.products.feature.service.validation

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification

interface Validator<T> {
    fun validate(entity: T)
    fun specification(block: (Root<T>, CriteriaQuery<*>, CriteriaBuilder) -> Predicate) = Specification { root, query, criteriaBuilder ->
        block(root, query, criteriaBuilder)
    }

    fun should(validation: Boolean, action: () -> Exception) {
        if (validation) {
            throw action()
        }
    }

    fun shouldNot(validation: Boolean, action: () -> Exception) {
        if (!validation) {
            throw action()
        }
    }
}

