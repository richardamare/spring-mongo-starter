package com.amarerichard.springmongostarter.controller

import com.amarerichard.springmongostarter.model.dto.UserDTO
import com.amarerichard.springmongostarter.service.domain.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
) {
    @GetMapping("/me")
    fun me(principal: Principal): ResponseEntity<UserDTO> {
        val user = userService.getByEmail(principal.name)
        return ResponseEntity.ok(user.toDTO())
    }
}