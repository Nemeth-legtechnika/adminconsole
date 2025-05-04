package com.nemethlegtechnika.products.controller

import com.nemethlegtechnika.common.security.UserInfo
import com.nemethlegtechnika.products.dto.user.UserDto
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api/product")
class TestController {
    @GetMapping("/test")
    fun test(): ResponseEntity<String> {
        return ResponseEntity.ok("Németh Légtechnika Kft.: Server is running.")
    }

    @GetMapping("/me")
    fun me(@AuthenticationPrincipal userInfo: UserInfo): ResponseEntity<UserDto> {
        val response = UserDto(
            username = userInfo.username,
            email = userInfo.email ?: "",
            roles = userInfo.roles
        )
        return ResponseEntity.ok(response)
    }
}