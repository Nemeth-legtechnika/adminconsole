package com.nemethlegtechnika.products.feature.service.repository

import com.nemethlegtechnika.products.model.Tag
import org.springframework.data.jpa.repository.JpaRepository

interface TagRepository: JpaRepository<Tag, Long> {
}