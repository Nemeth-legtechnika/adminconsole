package com.nemethlegtechnika.orders.config

import com.nemethlegtechnika.orders.feature.client.TokenClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import org.springframework.web.service.invoker.createClient
import java.time.Duration

@Configuration
class KeyCloakRestClientConfiguration {

    @Value("\${service.keycloak.url}")
    lateinit var url: String

    @Value("\${service.keycloak.realm}")
    lateinit var realm: String

    @Bean
    fun tokenClient(): TokenClient =
        RestClient.builder()
            .baseUrl("$url/realms/$realm")
            .requestFactory(getClientRequestFactory())
            .build()
            .let {
                RestClientAdapter.create(it)
            }
            .let {
                HttpServiceProxyFactory.builderFor(it)
                    .build()
                    .createClient<TokenClient>()
            }

    private fun getClientRequestFactory(): ClientHttpRequestFactory =
        ClientHttpRequestFactorySettings.defaults()
            .withConnectTimeout(Duration.ofSeconds(3))
            .withReadTimeout(Duration.ofSeconds(3))
            .let {
                ClientHttpRequestFactoryBuilder.simple().build(it)
            }
}