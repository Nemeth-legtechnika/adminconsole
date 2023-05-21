package com.nemethlegtechnika.products.db.model

import com.nemethlegtechnika.products.logic.config.Configuration
import com.nemethlegtechnika.products.util.round
import jakarta.persistence.*

@Entity
@Table(name = "product")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = [CascadeType.ALL])
    val attributes: List<Attribute> = listOf()

    @delegate:Transient
    val purchasePrice: Long by lazy {
        listPrice.round {
            val grossPrice = it * Configuration.AFA
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