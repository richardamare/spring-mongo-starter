package com.amarerichard.springmongostarter.service.data

import com.amarerichard.springmongostarter.exception.ResourceNotFoundException
import com.amarerichard.springmongostarter.model.document.User
import com.amarerichard.springmongostarter.model.request.SignUpRequest
import com.amarerichard.springmongostarter.repository.UserRepository
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

@ExtendWith(MockitoExtension::class)
class UserServiceImplTest {

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var passwordEncoder: PasswordEncoder

    @InjectMocks
    lateinit var userService: UserServiceImpl

    private lateinit var savedUser: User

    @BeforeEach
    fun setUp() {
        savedUser = User(
            id = ObjectId(),
            firstName = "firstName",
            lastName = "lastName",
            email = "email@example.com",
            passwordDigest = "password_digest"
        )
    }

    @Test
    fun `create() should create a new user`() {
        val request = SignUpRequest(
            firstName = savedUser.firstName,
            lastName = savedUser.lastName,
            email = savedUser.email,
            password = "password"
        )

        val encodedPassword = "encodedPassword"
        `when`(passwordEncoder.encode(request.password)).thenReturn(encodedPassword)

        `when`(userRepository.save(any(User::class.java))).thenReturn(savedUser)

        val result = userService.create(request)

        assertEquals(savedUser.id.toHexString(), result)
        verify(userRepository).save(any(User::class.java))
    }

    @Test
    fun `getById() should return a user by id`() {
        val id = savedUser.id

        `when`(userRepository.findById(id.toHexString())).thenReturn(Optional.of(savedUser))

        val result = userService.getById(id.toHexString())

        assertEquals(savedUser, result)
        verify(userRepository).findById(id.toHexString())
    }

    @Test
    fun `getByEmail() should return a user by email`() {
        val email = savedUser.email

        `when`(userRepository.findByEmail(email)).thenReturn(Optional.of(savedUser))

        val result = userService.getByEmail(email)

        assertEquals(savedUser, result)
        verify(userRepository).findByEmail(email)
    }

    @Test
    fun `loadUserByUsername() should return a user by email`() {
        val email = savedUser.email

        `when`(userRepository.findByEmail(email)).thenReturn(Optional.of(savedUser))

        val result = userService.loadUserByUsername(email)

        assertEquals(savedUser, result)
    }

    @Test
    fun `loadUserByUsername() should throw an exception if user is not found`() {
        val email = savedUser.email

        `when`(userRepository.findByEmail(email)).thenReturn(Optional.empty())

        assertThrows(ResourceNotFoundException::class.java) {
            userService.loadUserByUsername(email)
        }
    }

    @Test
    fun `loadUserByUsername() should throw an exception if username is null`() {
        assertThrows(IllegalArgumentException::class.java) {
            userService.loadUserByUsername(null)
        }
    }

    @Test
    fun `deleteById() should delete a user by id`() {
        val id = savedUser.id

        userService.deleteById(id.toHexString())

        verify(userRepository).deleteById(id.toHexString())
    }
}