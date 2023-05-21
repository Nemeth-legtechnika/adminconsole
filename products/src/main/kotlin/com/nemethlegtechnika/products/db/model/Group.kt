package com.nemethlegtechnika.products.db.model

import jakarta.persistence.*

@Entity
@Table(name = "group")
class Group : BaseEntity() {

    @Column(name = "name")
    val name: String = ""

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group", cascade = [CascadeType.ALL])
    val attributes: List<Attribute> = listOf()
}