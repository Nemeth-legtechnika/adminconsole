package com.nemethlegtechnika.orders.feature.controller

import com.nemethlegtechnika.common.security.UserInfo
import com.nemethlegtechnika.orders.domain.command.CreateOrder
import com.nemethlegtechnika.orders.domain.command.UpdateOrder
import com.nemethlegtechnika.orders.domain.dto.OrderDto
import com.nemethlegtechnika.orders.domain.mapper.toAdapter
import com.nemethlegtechnika.orders.domain.mapper.toOrderDto
import com.nemethlegtechnika.orders.feature.usecase.ModifyOrderUseCase
import com.nemethlegtechnika.orders.feature.usecase.QueryOrderUseCase
import com.nemethlegtechnika.orders.util.entryPoint
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
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
class OrderController(
    private val queryOrderUseCase: QueryOrderUseCase,
    private val modifyOrderUseCase: ModifyOrderUseCase
) {

    @GetMapping("/test")
    fun test(@AuthenticationPrincipal userInfo: UserInfo): ResponseEntity<String> {
        return ResponseEntity.ok("username: ${userInfo.username}, roles: ${userInfo.roles}")
    }

    @GetMapping
    fun getAll(): ResponseEntity<List<OrderDto>> = entryPoint {
        queryOrderUseCase.getAll().map {
            it.toOrderDto()
        }.let {
            ResponseEntity.ok(it)
        }
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<OrderDto> = entryPoint {
        queryOrderUseCase.getById(id).toOrderDto().let {
            ResponseEntity.ok(it)
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    fun create(@RequestBody command: CreateOrder): ResponseEntity<OrderDto> = entryPoint {
        modifyOrderUseCase.createOrder(command.toAdapter()).toOrderDto().let {
            ResponseEntity.ok(it)
        }
    }

    @PutMapping()
    @PreAuthorize("hasRole('admin')")
    fun update(@RequestBody command: UpdateOrder): ResponseEntity<OrderDto> = entryPoint {
        modifyOrderUseCase.updateOrder(command.toAdapter()).toOrderDto().let {
            ResponseEntity.ok(it)
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    fun delete(@PathVariable id: String): ResponseEntity<Unit> = entryPoint {
        modifyOrderUseCase.deleteOrder(id).let {
            ResponseEntity.noContent().build()
        }
    }
}