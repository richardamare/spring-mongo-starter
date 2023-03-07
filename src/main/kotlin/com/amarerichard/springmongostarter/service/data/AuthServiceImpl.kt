package com.amarerichard.springmongostarter.service.data

import com.amarerichard.springmongostarter.model.dto.AuthDTO
import com.amarerichard.springmongostarter.model.request.SignInRequest
import com.amarerichard.springmongostarter.model.request.SignUpRequest
import com.amarerichard.springmongostarter.service.domain.AuthService
import com.amarerichard.springmongostarter.service.domain.JwtService
import com.amarerichard.springmongostarter.service.domain.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthServiceImpl(
    private val userService: UserService,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder,
) : AuthService {
    override fun signUp(req: SignUpRequest): AuthDTO {
        val userId = userService.create(req)
        val user = userService.getById(userId)
        val token = Optional.ofNullable(jwtService.generateToken(user))
            .orElseThrow {
                userService.deleteById(user.id.toHexString())
                IllegalStateException("Failed to generate token")
            }
        return AuthDTO(token)
    }

    override fun signIn(req: SignInRequest): AuthDTO {
        val user = userService.getByEmail(req.email)

        if (!passwordEncoder.matches(req.password, user.password)) {
            throw IllegalArgumentException("Invalid credentials")
        }

        val token = jwtService.generateToken(user)
            ?: throw IllegalStateException("Failed to generate token")

        return AuthDTO(token)
    }
}