package com.nemethlegtechnika.orders.controller

import com.nemethlegtechnika.orders.domain.command.CreateOrder
import com.nemethlegtechnika.orders.domain.command.UpdateOrder
import com.nemethlegtechnika.orders.domain.dto.OrderDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/order")
class OrderController {

    @GetMapping
    fun getAll(): ResponseEntity<List<OrderDto>> = runBlocking(Dispatchers.IO) {
        TODO("Implementation missing")
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<OrderDto> = runBlocking(Dispatchers.IO) {
        TODO("Implementation missing")
    }

    @PostMapping
    fun create(@RequestBody command: CreateOrder): ResponseEntity<OrderDto> = runBlocking(Dispatchers.IO) {
        TODO("Implementation missing")
    }

    @PutMapping()
    fun update(@RequestBody command: UpdateOrder): ResponseEntity<OrderDto> = runBlocking(Dispatchers.IO) {
        TODO("Implementation missing")
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Unit> = runBlocking(Dispatchers.IO) {
        TODO("Implementation missing")
    }
}