package com.nemethlegtechnika.products.db.model

import com.nemethlegtechnika.products.util.round
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(name = "product", uniqueConstraints = [
    UniqueConstraint(columnNames = ["number", "company_id"])
])
class Product : BaseEntity() {

    @Column(name = "name", nullable = false)
    var name: String? = null

    @Column(name = "number", nullable = false)
    var number: String? = null

    @Column(name = "description", nullable = true)
    var description: String? = null

    @Column(name = "list_price", nullable = false)
    var listPrice: Long? = null

    @Column(name = "discount", nullable = false)
    var discount: Double? = null

    @Column(name = "margin", nullable = false)
    var margin: Double? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id")
    var company: Company? = null

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST])
    @JoinTable(
        name = "product_tag",
        joinColumns = [JoinColumn(name = "product_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")]
    )
    var tags: MutableList<Tag> = mutableListOf()

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    var group: ProductGroup? = null

    fun purchasePrice(afa: CustomProperty): Long? {
        return listPrice?.round {
            val grossPrice = it * afa.value<Double>()
            grossPrice * (1.0 - (discount ?: 0.0))
        }
    }

    fun sellPrice(afa: CustomProperty): Long? {
        return purchasePrice(afa)?.round {
            it * (1.0 + (margin ?: 0.0))
        }
    }
}