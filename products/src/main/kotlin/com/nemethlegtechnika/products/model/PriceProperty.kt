package com.nemethlegtechnika.products.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "price_property")
class PriceProperty : BaseEntity() {

    @Column(name = "name", unique = true, nullable = false)
    val name: String? = null

    @Column(name = "value", nullable = false)
    val value: Double? = null
}