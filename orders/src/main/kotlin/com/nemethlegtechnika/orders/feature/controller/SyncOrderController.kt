package com.nemethlegtechnika.orders.feature.controller

import com.nemethlegtechnika.orders.domain.dto.OrderDto
import com.nemethlegtechnika.orders.domain.mapper.toOrderDto
import com.nemethlegtechnika.orders.feature.usecase.SyncOrderUseCase
import com.nemethlegtechnika.orders.util.entryPoint
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/order/sync")
class SyncOrderController(
    private val syncOrderUseCase: SyncOrderUseCase,
) {

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    fun syncAll(@RequestParam(defaultValue = "") ids: List<String>): ResponseEntity<List<OrderDto>> = entryPoint {
        syncOrderUseCase.syncAll(ids).map {
            it.toOrderDto()
        }.let {
            ResponseEntity.ok(it)
        }
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    fun syncById(@PathVariable id: String): ResponseEntity<OrderDto> = entryPoint {
        syncOrderUseCase.syncById(id).toOrderDto().let {
            ResponseEntity.ok(it)
        }
    }
}