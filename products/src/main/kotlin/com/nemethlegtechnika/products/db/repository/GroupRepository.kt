package com.nemethlegtechnika.products.db.repository

import com.nemethlegtechnika.products.db.model.Group
import org.springframework.data.jpa.repository.JpaRepository

interface GroupRepository: JpaRepository<Group, Long> {
}