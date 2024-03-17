package com.nemethlegtechnika.products.db.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "company")
class Company : BaseEntity() {

    @Column(name = "name", nullable = false, unique = true)
    var name: String? = null

    @Column(name = "discount", nullable = false)
    var discount: Double? = null

    @Column(name = "margin", nullable = false)
    var margin: Double? = null

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val products: MutableList<Product> = mutableListOf()

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val groups: MutableList<ProductGroup> = mutableListOf()
}