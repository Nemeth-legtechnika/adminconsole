package com.nemethlegtechnika.products.mapper

import com.nemethlegtechnika.products.model.Product
import com.nemethlegtechnika.products.model.purchasePrice
import com.nemethlegtechnika.products.model.sellPrice
import com.nemethlegtechnika.products.dto.product.CreateProductDto
import com.nemethlegtechnika.products.dto.product.GetProductDto
import com.nemethlegtechnika.products.dto.product.UpdateProductDto
import com.nemethlegtechnika.products.feature.service.interfaces.CompanyService
import com.nemethlegtechnika.products.feature.service.interfaces.PropertyService
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants
import org.mapstruct.Named
import org.springframework.beans.factory.annotation.Autowired

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = [TagMapper::class, CompanyService::class])
abstract class ProductMapper {

    @Autowired
    protected lateinit var propertyService: PropertyService

    @Mapping(target = "company", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "id", ignore = true)
    abstract fun map(createProductDto: CreateProductDto): Product

    @Mapping(source = "company.name", target = "companyName")
    @Mapping(source = "group.name", target = "groupName")
    @Mapping(source = "product", target = "purchasePrice", qualifiedByName = ["purchasePrice"])
    @Mapping(source = "product", target = "sellPrice", qualifiedByName = ["sellPrice"])
    abstract fun map(product: Product): GetProductDto

    @Mapping(target = "company", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "group", ignore = true)
    abstract fun map(updateProductDto: UpdateProductDto): Product

    @Named("purchasePrice")
    fun mapPurchasePrice(product: Product): Long? {
        return product.purchasePrice(propertyService.afa)
    }

    @Named("sellPrice")
    fun mapSellPrice(product: Product): Long? {
        return product.sellPrice(propertyService.afa)
    }
}