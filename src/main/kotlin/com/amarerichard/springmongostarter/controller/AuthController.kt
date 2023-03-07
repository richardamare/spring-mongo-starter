package com.amarerichard.springmongostarter.controller

import com.amarerichard.springmongostarter.model.dto.AuthDTO
import com.amarerichard.springmongostarter.model.request.SignInRequest
import com.amarerichard.springmongostarter.model.request.SignUpRequest
import com.amarerichard.springmongostarter.service.domain.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/sign-up")
    fun signUp(@RequestBody body: SignUpRequest): ResponseEntity<AuthDTO> {
        val result = authService.signUp(body)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/sign-in")
    fun signIn(@RequestBody body: SignInRequest): ResponseEntity<AuthDTO> {
        val result = authService.signIn(body)
        return ResponseEntity.ok(result)
    }
}