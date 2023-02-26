package com.amarerichard.springmongostarter.service.data

import com.amarerichard.springmongostarter.model.document.User
import com.amarerichard.springmongostarter.model.request.UserCreateRequest
import com.amarerichard.springmongostarter.repository.UserRepository
import com.amarerichard.springmongostarter.service.domain.UserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl constructor(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) : UserService, UserDetailsService {
    override fun create(request: UserCreateRequest): User {
        val user = User(
            name = request.name,
            email = request.email,
            passwordDigest = passwordEncoder.encode(request.password)
        )

        return userRepository.save(user)
    }

    override fun getAll(): List<User> {
        return userRepository.findAll()
    }

    override fun getById(id: String): User {
        return userRepository.findById(id).orElseThrow {
            IllegalArgumentException("User with id $id not found")
        }
    }

    override fun findByEmail(email: String): User {
        return userRepository.findByEmail(email).orElseThrow {
            IllegalArgumentException("User with email $email not found")
        }
    }

    override fun deleteById(id: String) {
        userRepository.deleteById(id)
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null) {
            throw IllegalArgumentException("Username cannot be null")
        }
        return findByEmail(username)
    }
}