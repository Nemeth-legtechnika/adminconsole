package com.nemethlegtechnika.products.db.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

@Entity
@Table(name = "tag")
class Tag : BaseEntity() {

    @Column(name = "name")
    var name: String? = null

    @Column(name = "color", length = 7)
    var color: String? = null

    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST])
    var product: MutableList<Product> = mutableListOf()
}