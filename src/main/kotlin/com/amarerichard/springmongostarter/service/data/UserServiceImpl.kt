package com.amarerichard.springmongostarter.service.data

import com.amarerichard.springmongostarter.exception.DuplicateResourceException
import com.amarerichard.springmongostarter.exception.ResourceNotFoundException
import com.amarerichard.springmongostarter.model.document.User
import com.amarerichard.springmongostarter.model.request.UserCreateRequest
import com.amarerichard.springmongostarter.repository.UserRepository
import com.amarerichard.springmongostarter.service.domain.UserService
import com.mongodb.DuplicateKeyException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl constructor(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) : UserService, UserDetailsService {
    override fun create(request: UserCreateRequest): String {
        val user = User(
            name = request.name,
            email = request.email,
            passwordDigest = passwordEncoder.encode(request.password)
        )

        return try {
            userRepository.save(user).id.toHexString()
        } catch (e: DuplicateKeyException) {
            throw DuplicateResourceException("User with email ${request.email} already exists")
        }
    }

    override fun getAll(): List<User> {
        return userRepository.findAll()
    }

    override fun getById(id: String): User {
        return userRepository.findById(id).orElseThrow {
            ResourceNotFoundException("User with id $id not found")
        }
    }

    override fun findByEmail(email: String): User {
        return userRepository.findByEmail(email).orElseThrow {
            ResourceNotFoundException("User with email $email not found")
        }
    }

    override fun deleteById(id: String) {
        userRepository.deleteById(id)
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null) {
            throw ResourceNotFoundException("Username must not be null")
        }
        return findByEmail(username)
    }
}