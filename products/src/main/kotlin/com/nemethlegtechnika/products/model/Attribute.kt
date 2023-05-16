package com.nemethlegtechnika.products.model

import jakarta.persistence.*
import org.hibernate.annotations.DiscriminatorFormula

@Entity
@Table(name = "attribute")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("case when stringValues is not null then 'string' else 'boolean' end")
abstract class Attribute : BaseEntity() {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "product_id")
    val product: Product? = null

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "group_id")
    val group: Group? = null

    abstract val value: Any
}

@Entity
@DiscriminatorValue("boolean")
class BooleanAttribute : Attribute() {

    @Column(name = "boolean_value", nullable = true)
    val booleanValue: Boolean = false

    override val value: Boolean
        get() = booleanValue
}

@Entity
@DiscriminatorValue("string")
class StringAttribute : Attribute() {

    @Column(name = "string_value", nullable = true)
    val stringValue: String = ""

    override val value: String
        get() = stringValue
}