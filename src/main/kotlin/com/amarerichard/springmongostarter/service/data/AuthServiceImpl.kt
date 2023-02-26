package com.amarerichard.springmongostarter.service.data

import com.amarerichard.springmongostarter.model.request.SignInRequest
import com.amarerichard.springmongostarter.model.request.UserCreateRequest
import com.amarerichard.springmongostarter.model.response.AuthResponse
import com.amarerichard.springmongostarter.service.domain.AuthService
import com.amarerichard.springmongostarter.service.domain.JwtService
import com.amarerichard.springmongostarter.service.domain.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthServiceImpl constructor(
    private val userService: UserService,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder,
) : AuthService {
    override fun signUp(req: UserCreateRequest): AuthResponse {
        val user = userService.create(req)
        val token = Optional.ofNullable(jwtService.generateToken(user))
            .orElseThrow {
                userService.deleteById(user.id.toHexString())
                IllegalStateException("Failed to generate token")
            }
        return AuthResponse(token)
    }

    override fun signIn(req: SignInRequest): AuthResponse {
        val user = userService.findByEmail(req.email)

        if (!passwordEncoder.matches(req.password, user.password)) {
            throw IllegalArgumentException("Invalid credentials")
        }

        val token = jwtService.generateToken(user)
            ?: throw IllegalStateException("Failed to generate token")

        return AuthResponse(token)
    }
}