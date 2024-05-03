package com.nemethlegtechnika.products.db.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "product_group")
class ProductGroup : BaseEntity() {

    @Column(name = "name", nullable = false)
    var name: String? = null

    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
    val products: MutableList<Product> = mutableListOf()
}