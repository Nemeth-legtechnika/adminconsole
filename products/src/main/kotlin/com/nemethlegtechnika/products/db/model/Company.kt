package com.nemethlegtechnika.products.db.model

import jakarta.persistence.*

@Entity
@Table(name = "company")
class Company : BaseEntity() {

    @Column(name = "name", nullable = false)
    val name: String = ""

    @Column(name = "discount", nullable = false)
    val discount: Double = 0.0

    @Column(name = "margin", nullable = false)
    val margin: Double = 0.0

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val products: List<Product> = listOf()
}