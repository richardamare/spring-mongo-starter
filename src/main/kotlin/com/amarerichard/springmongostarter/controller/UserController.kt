package com.amarerichard.springmongostarter.controller

import com.amarerichard.springmongostarter.model.document.User
import com.amarerichard.springmongostarter.service.domain.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/v1/users")
class UserController constructor(
    private val userService: UserService,
) {
    @GetMapping("/me")
    fun me(principal: Principal): ResponseEntity<User> {
        val user = userService.findByEmail(principal.name)
        return ResponseEntity.ok(user)
    }
}