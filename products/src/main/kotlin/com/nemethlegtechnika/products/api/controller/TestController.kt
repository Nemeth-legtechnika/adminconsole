package com.nemethlegtechnika.products.api.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @GetMapping("/test")
    fun test(): ResponseEntity<String> {
        return ResponseEntity.ok("Németh Légtechnika Kft.: Server is running.")
    }
}