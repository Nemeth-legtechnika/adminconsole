package com.nemethlegtechnika.products.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.UpdateTimestamp
import java.util.Date

@MappedSuperclass
abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "base_entity_seq")
    @SequenceGenerator(name = "base_entity_seq")
    @Column(name = "id", nullable = false)
    val id: Long? = null

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    val createdAt: Date? = null

    @UpdateTimestamp
    @Column(name = "updated_at")
    val updatedAt: Date? = null
}