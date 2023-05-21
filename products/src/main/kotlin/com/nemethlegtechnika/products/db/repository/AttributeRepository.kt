package com.nemethlegtechnika.products.db.repository

import com.nemethlegtechnika.products.db.model.Attribute
import org.springframework.data.jpa.repository.JpaRepository

interface AttributeRepository: JpaRepository<Attribute, Long> {

}