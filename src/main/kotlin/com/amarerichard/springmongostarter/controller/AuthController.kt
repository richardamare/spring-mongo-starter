package com.amarerichard.springmongostarter.controller

import com.amarerichard.springmongostarter.model.request.SignInRequest
import com.amarerichard.springmongostarter.model.request.UserCreateRequest
import com.amarerichard.springmongostarter.model.response.handleExceptionResponse
import com.amarerichard.springmongostarter.service.domain.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController constructor(
    private val authService: AuthService,
) {
    @PostMapping("/sign-up")
    fun signUp(@RequestBody body: UserCreateRequest): ResponseEntity<Any> {
        return try {
            val result = authService.signUp(body)
            ResponseEntity.ok(result)
        } catch (e: Exception) {
            handleExceptionResponse(e)
        }
    }

    @PostMapping("/sign-in")
    fun signIn(@RequestBody body: SignInRequest): ResponseEntity<Any> {
        return try {
            val result = authService.signIn(body)
            ResponseEntity.ok(result)
        } catch (e: Exception) {
            handleExceptionResponse(e)
        }
    }
}