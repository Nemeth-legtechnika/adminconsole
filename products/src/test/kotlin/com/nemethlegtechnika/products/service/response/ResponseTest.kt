package com.nemethlegtechnika.products.service.response

import io.mockk.spyk
import io.mockk.verify
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class ResponseTest {

    private val resolver: ResponseResolver = spyk()
    private var cnt: Int? = null

    data class From(
        val value: Int = 0,
        val name: String = "",
    )

    data class To(
        val value: String = ""
    )

    private fun map(from: From): To {
        return To("Value for ${from.name} is ${from.value}")
    }

    private fun mapWithCnt(from: From): To {
        cnt = cnt?.plus(1)
        return To("Value for ${from.name} is ${from.value}")
    }

    @Test
    fun `Test single read response`() {
        val single = single(::map) { From(1, "One") } read resolver

        assertThat(single.statusCode).isEqualTo(HttpStatus.OK)
        val body = single.body
        assertThat(body).isNotNull
        assertThat(body!!.value).isEqualTo("Value for One is 1")
        verify(exactly = 0) { resolver.write(any()) }
        verify(exactly = 1) { resolver.read(any()) }
    }

    @Test
    fun `Test single write response`() {
        val single = single(::map) { From(1, "One") } write resolver

        assertThat(single.statusCode).isEqualTo(HttpStatus.OK)
        val body = single.body
        assertThat(body).isNotNull
        assertThat(body!!.value).isEqualTo("Value for One is 1")
        verify(exactly = 1) { resolver.write(any()) }
        verify(exactly = 0) { resolver.read(any()) }
    }

    @Test
    fun `Test list read response`() {
        cnt = 0
        val list = list(::mapWithCnt) { listOf(From(1, "One"), From(2, "Two")) } read resolver

        assertThat(list.statusCode).isEqualTo(HttpStatus.OK)
        val body = list.body
        assertThat(body).isNotNull
        assertThat(body!!.size).isEqualTo(2)
        assertThat(body[0].value).isEqualTo("Value for One is 1")
        assertThat(body[1].value).isEqualTo("Value for Two is 2")
        assertThat(cnt).isEqualTo(2)
        verify(exactly = 0) { resolver.write(any()) }
        verify(exactly = 1) { resolver.read(any()) }
    }

    @Test
    fun `Test list write response`() {
        cnt = 0
        val list = list(::mapWithCnt) { listOf(From(1, "One"), From(2, "Two")) } write resolver

        assertThat(list.statusCode).isEqualTo(HttpStatus.OK)
        val body = list.body
        assertThat(body).isNotNull
        assertThat(body!!.size).isEqualTo(2)
        assertThat(body[0].value).isEqualTo("Value for One is 1")
        assertThat(body[1].value).isEqualTo("Value for Two is 2")
        assertThat(cnt).isEqualTo(2)
        verify(exactly = 1) { resolver.write(any()) }
        verify(exactly = 0) { resolver.read(any()) }
    }
}