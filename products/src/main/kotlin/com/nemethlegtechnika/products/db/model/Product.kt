package com.nemethlegtechnika.products.db.model

import com.nemethlegtechnika.products.service.Properties
import com.nemethlegtechnika.products.util.round
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import jakarta.persistence.Transient
import jakarta.persistence.UniqueConstraint

@Entity
@Table(name = "product", uniqueConstraints = [
    UniqueConstraint(columnNames = ["number", "company_id"])
])
class Product : BaseEntity() {

    @Column(name = "name", nullable = false)
    val name: String = ""

    @Column(name = "number", nullable = false)
    val number: String = ""

    @Column(name = "type", nullable = true)
    val type: String = ""

    @Column(name = "description", nullable = true)
    val description: String = ""

    @Column(name = "list_price", nullable = false)
    val listPrice: Long = 0

    @Column(name = "discount", nullable = false)
    val discount: Double = 0.0

    @Column(name = "margin", nullable = false)
    val margin: Double = 0.0

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id")
    val company: Company? = null

    @OneToOne(mappedBy = "product", cascade = [CascadeType.ALL])
    val attributes: GroupTag? = null

    @delegate:Transient
    val purchasePrice: Long by lazy {
        listPrice.round {
            val grossPrice = it * Properties.AFA
            grossPrice * (1.0 - discount)
        }
    }

    @delegate:Transient
    val sellPrice: Long by lazy {
        purchasePrice.round {
            it * (1.0 + margin)
        }
    }
}