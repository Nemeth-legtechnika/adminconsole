package com.nemethlegtechnika.products.db.model

import jakarta.persistence.*

@Entity
@Table(name = "company")
class Company (
    @Column(name = "name", nullable = false)
    var name: String = "",

    @Column(name = "discount", nullable = false)
    var discount: Double = 0.0,

    @Column(name = "margin", nullable = false)
    var margin: Double = 0.0,

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val products: List<Product> = mutableListOf()
) : BaseEntity()