package com.nemethlegtechnika.products.db.repository

import com.nemethlegtechnika.products.db.model.ProductGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface GroupRepository: JpaRepository<ProductGroup, Long> {
    fun findAllByCompanyName(companyName: String): List<ProductGroup>

    @Query("""
        select case when count(g) > 0 then true else false end 
        from ProductGroup g 
        where g.company.name = :companyName 
            and g.name = 'Default'
    """)
    fun defaultGroupExists(companyName: String): Boolean

    @Query("select g from ProductGroup g where g.name = 'Default'")
    fun findAllDefaultGroup(): List<ProductGroup>

    @Query("select g from ProductGroup g where g.name = 'Default' and g.company.name = :companyName")
    fun findDefaultGroup(companyName: String): Optional<ProductGroup>

    @Query("select g from ProductGroup g where g.name = 'Default' and g.company.id = :id")
    fun findDefaultGroup(id: Long): Optional<ProductGroup>

    @Query("""
        select case when count(g) > 0 then true else false end 
        from ProductGroup g 
        where g.id = :id
            and g.name = 'Default'
    """)
    fun isDefaultGroup(id: Long): Boolean

    fun existsByName(name: String): Boolean
}