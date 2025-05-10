package com.nemethlegtechnika.orders.feature.adapter

import com.nemethlegtechnika.common.exception.BackendException
import com.nemethlegtechnika.orders.domain.command.ProductQuantity
import com.nemethlegtechnika.orders.domain.mapper.toProduct
import com.nemethlegtechnika.orders.domain.model.Product
import com.nemethlegtechnika.orders.feature.client.ProductClient
import com.nemethlegtechnika.orders.feature.port.RequestProductPort
import com.nemethlegtechnika.orders.feature.port.RetrieveAccessTokenPort
import com.nemethlegtechnika.orders.feature.port.bearerToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class RequestProductAdapter(
    private val productClient: ProductClient,
    private val retrieveAccessTokenPort: RetrieveAccessTokenPort,
): RequestProductPort {
    override suspend fun getProducts(products: List<ProductQuantity>): List<Product> = withContext(Dispatchers.IO) {
        val idWithQuantity = products.associate { it.id to it.quantity }
        retrieveAccessTokenPort.accessToken().let {
            productClient.getProducts(idWithQuantity.keys.toList(), it.bearerToken)
        }.map { product ->
            product.toProduct() { id ->
                idWithQuantity[id] ?: throw BackendException("Product with id $id not found in the provided map")
            }
        }
    }
}