package com.nemethlegtechnika.products.model

import com.nemethlegtechnika.products.config.Configuration
import com.nemethlegtechnika.products.util.round
import jakarta.persistence.*
import kotlin.math.round
import kotlin.time.times

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

    val purchasePrice: Long
        get() {
            return listPrice.round {
                val grossPrice = it * Configuration.AFA
                grossPrice * (1.0 - discount)
            }
        }

    val sellPrice: Long
        get() {
            return purchasePrice.round {
                it * (1.0 + margin)
            }
        }
}