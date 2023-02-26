package com.amarerichard.springmongostarter.service.data

import com.amarerichard.springmongostarter.model.document.User
import com.amarerichard.springmongostarter.model.request.UserCreateRequest
import com.amarerichard.springmongostarter.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.crypto.password.PasswordEncoder

@ExtendWith(MockitoExtension::class)
class UserServiceImplTest {

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var passwordEncoder: PasswordEncoder

    @InjectMocks
    lateinit var userServiceImpl: UserServiceImpl

    @Test
    fun `create should save user with encoded password`() {
        val request = UserCreateRequest(name = "John", email = "john@example.com", password = "password")

        val encodedPassword = "encodedPassword"
        `when`(passwordEncoder.encode(request.password)).thenReturn(encodedPassword)

        val savedUser = User(
            name = request.name,
            email = request.email,
            passwordDigest = encodedPassword
        )
        `when`(userRepository.save(any(User::class.java))).thenReturn(savedUser)

        val result = userServiceImpl.create(request)

        assertEquals(savedUser, result)
        verify(userRepository).save(savedUser)
    }
}