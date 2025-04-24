package com.nemethlegtechnika.products.controller

import com.nemethlegtechnika.products.dto.user.UserInfo
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api")
class TestController {
    @GetMapping("/test")
    fun test(): ResponseEntity<String> {
        return ResponseEntity.ok("Németh Légtechnika Kft.: Server is running.")
    }

    @GetMapping("/me")
    fun me(@AuthenticationPrincipal userInfo: UserInfo): ResponseEntity<String> {
        return ResponseEntity.ok("Logged in user: ${userInfo.username}, email: ${userInfo.email}, roles: ${userInfo.roles}")
    }
}