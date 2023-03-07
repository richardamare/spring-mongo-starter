package com.amarerichard.springmongostarter.service.data

import com.amarerichard.springmongostarter.exception.DuplicateResourceException
import com.amarerichard.springmongostarter.exception.ResourceNotFoundException
import com.amarerichard.springmongostarter.model.document.User
import com.amarerichard.springmongostarter.model.request.SignUpRequest
import com.amarerichard.springmongostarter.repository.UserRepository
import com.amarerichard.springmongostarter.service.domain.UserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) : UserService, UserDetailsService {
    override fun create(request: SignUpRequest): String {
        val user = User(
            firstName = request.firstName,
            lastName = request.lastName,
            email = request.email,
            passwordDigest = passwordEncoder.encode(request.password)
        )

        return userRepository.save(user)
            .runCatching { id.toHexString() }
            .getOrElse {
                throw DuplicateResourceException("User with email ${request.email} already exists")
            }
    }

    override fun getById(id: String): User {
        return userRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("User with id $id not found") }
    }

    override fun getByEmail(email: String): User {
        return userRepository.findByEmail(email)
            .orElseThrow { ResourceNotFoundException("User with email $email not found") }
    }

    override fun deleteById(id: String) {
        userRepository.deleteById(id)
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null) {
            throw IllegalArgumentException("Username is required")
        }
        return getByEmail(username)
    }
}