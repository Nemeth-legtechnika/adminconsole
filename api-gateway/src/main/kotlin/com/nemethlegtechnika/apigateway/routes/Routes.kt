package com.nemethlegtechnika.apigateway.routes

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.function.RequestPredicates
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse

@Configuration
class Routes {
    @Value("\${service.product.url}")
    private lateinit var productServiceUrl: String

    @Value("\${service.order.url}")
    private lateinit var orderServiceUrl: String

    @Bean
    fun productServiceRoute(): RouterFunction<ServerResponse> {
        return GatewayRouterFunctions.route("product-service")
            .route(RequestPredicates.path("/api/product/**"), HandlerFunctions.http(productServiceUrl))
            .route(RequestPredicates.path("/api/company/**"), HandlerFunctions.http(productServiceUrl))
            .route(RequestPredicates.path("/api/group/**"), HandlerFunctions.http(productServiceUrl))
            .route(RequestPredicates.path("/api/property/**"), HandlerFunctions.http(productServiceUrl))
            .route(RequestPredicates.path("/api/tag/**"), HandlerFunctions.http(productServiceUrl))
            .build()
    }

    @Bean
    fun orderServiceRoute(): RouterFunction<ServerResponse> {
        return GatewayRouterFunctions.route("order-service")
            .route(RequestPredicates.path("/api/order/**"), HandlerFunctions.http(orderServiceUrl))
            .build()
    }
}