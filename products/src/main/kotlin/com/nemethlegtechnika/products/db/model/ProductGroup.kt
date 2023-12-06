package com.nemethlegtechnika.products.db.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(name = "product_group", uniqueConstraints = [
    UniqueConstraint(columnNames = ["name", "company_id"])
])
class ProductGroup : BaseEntity() {

    @Column(name = "name", nullable = false)
    var name: String? = null

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST], optional = false)
    @JoinColumn(name = "company_id")
    var company: Company? = null

    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST])
    val products: MutableList<Product> = mutableListOf()
}