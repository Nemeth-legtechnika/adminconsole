package com.nemethlegtechnika.products.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "product_group")
class ProductGroup : BaseEntity() {

    @Column(name = "name")
    val name: String = ""

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productGroup", cascade = [CascadeType.ALL])
    val attributes: List<GroupTag> = listOf()
}