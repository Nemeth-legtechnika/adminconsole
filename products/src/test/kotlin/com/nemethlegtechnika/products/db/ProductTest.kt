package com.nemethlegtechnika.products.db

import com.nemethlegtechnika.products.db.model.CustomProperty
import com.nemethlegtechnika.products.db.model.Product
import com.nemethlegtechnika.products.exception.BackendException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProductTest {

    @Test
    fun `Test purchasePrice`() {
        val product = Product().apply {
            listPrice = 100L
            discount = 0.5
        }

        val afa = CustomProperty().apply {
            value = 1.27.toString()
        }

        val result = product.purchasePrice(afa)

        assertEquals(64L, result)
    }

    @Test
    fun `Test purchasePrice with null discount`() {
        val product = Product().apply {
            listPrice = 100L
        }

        val afa = CustomProperty().apply {
            value = 1.27.toString()
        }

        val result = product.purchasePrice(afa)

        assertEquals(127L, result)
    }

    @Test
    fun `Test purchasePrice with null listPrice`() {
        val product = Product().apply {
            discount = 0.5
        }

        val afa = CustomProperty().apply {
            value = 1.27.toString()
        }

        val result = product.purchasePrice(afa)

        assertEquals(null, result)
    }

    @Test
    fun `Test purchasePrice with null afa value`() {
        val product = Product().apply {
            listPrice = 100L
            discount = 0.5
        }

        val afa = CustomProperty().apply {
            value = "null"
        }

        val exception = assertThrows<BackendException> { product.purchasePrice(afa) }

        assertEquals("[AdminConsole][Backend]: Illegal value for Double: null", exception.message)
    }

    @Test
    fun `Test sellPrice`() {
        val product = Product().apply {
            listPrice = 100L
            discount = 0.5
            margin = 0.5
        }

        val afa = CustomProperty().apply {
            value = 1.27.toString()
        }

        val result = product.sellPrice(afa)

        assertEquals(96L, result)
    }

    @Test
    fun `Test sellPrice with null margin`() {
        val product = Product().apply {
            listPrice = 100L
            discount = 0.5
        }

        val afa = CustomProperty().apply {
            value = 1.27.toString()
        }

        val result = product.sellPrice(afa)

        assertEquals(64L, result)
    }
}