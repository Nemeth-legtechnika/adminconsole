package com.nemethlegtechnika.products.db.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "group_tag")
class GroupTag : BaseEntity() {

    @Column(name = "name")
    val name: String? = null

    @Column(name = "color", length = 7)
    val color: String? = null

    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "product_id", unique = true)
    val product: Product? = null

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "group_id")
    val productGroup: ProductGroup? = null
}