package com.nemethlegtechnika.orders

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.nemethlegtechnika")
class OrdersApplication

fun main(args: Array<String>) {
    runApplication<OrdersApplication>(*args)
}
