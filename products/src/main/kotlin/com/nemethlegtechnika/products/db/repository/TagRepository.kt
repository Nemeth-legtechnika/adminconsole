package com.nemethlegtechnika.products.db.repository

import com.nemethlegtechnika.products.db.model.Tag
import org.springframework.data.jpa.repository.JpaRepository

interface TagRepository: JpaRepository<Tag, Long> {
}