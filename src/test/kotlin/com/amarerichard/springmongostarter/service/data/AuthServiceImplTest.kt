package com.amarerichard.springmongostarter.service.data

import com.amarerichard.springmongostarter.exception.ResourceNotFoundException
import com.amarerichard.springmongostarter.model.document.User
import com.amarerichard.springmongostarter.model.request.SignInRequest
import com.amarerichard.springmongostarter.model.request.SignUpRequest
import com.amarerichard.springmongostarter.service.domain.JwtService
import com.amarerichard.springmongostarter.service.domain.UserService
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.crypto.password.PasswordEncoder

/**
 *
 * @project spring-mongo-starter
 * @author richardamare on 07.03.2023
 */
@ExtendWith(MockitoExtension::class)
class AuthServiceImplTest {

    @Mock
    lateinit var userService: UserService

    @Mock
    lateinit var passwordEncoder: PasswordEncoder

    @Mock
    lateinit var jwtService: JwtService

    @InjectMocks
    lateinit var authService: AuthServiceImpl

    lateinit var savedUser: User

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
    fun `signUp() should create a new user`() {
        val req = SignUpRequest(
            firstName = "firstName",
            lastName = "lastName",
            email = "email@example.com",
            password = "password"
        )

        `when`(userService.create(req)).thenReturn(savedUser.id.toHexString())
        `when`(userService.getById(savedUser.id.toHexString())).thenReturn(savedUser)
        `when`(jwtService.generateToken(savedUser)).thenReturn("token")

        val result = authService.signUp(req)

        assertEquals("token", result.token)
    }

    @Test
    fun `signIn() should return a token`() {
        val req = SignInRequest(
            email = "email@example.com",
            password = "password"
        )

        `when`(userService.getByEmail(req.email)).thenReturn(savedUser)
        `when`(passwordEncoder.matches(req.password, savedUser.passwordDigest)).thenReturn(true)
        `when`(jwtService.generateToken(savedUser)).thenReturn("token")

        val result = authService.signIn(req)

        assertEquals("token", result.token)
    }

    @Test
    fun `signIn() should throw an exception if the password is incorrect`() {
        val req = SignInRequest(
            email = "email@example.com",
            password = "password"
        )

        `when`(userService.getByEmail(req.email)).thenReturn(savedUser)
        `when`(passwordEncoder.matches(req.password, savedUser.passwordDigest)).thenReturn(false)

        assertThrows(IllegalArgumentException::class.java) {
            authService.signIn(req)
        }
    }

    @Test
    fun `signIn() should throw an exception if the user is not found`() {
        val req = SignInRequest(
            email = "email@example.com",
            password = "password"
        )

        `when`(userService.getByEmail(req.email))
            .thenThrow(ResourceNotFoundException("User not found"))

        assertThrows(ResourceNotFoundException::class.java) {
            authService.signIn(req)
        }
    }

    @Test
    fun `signIn() should throw an exception if the token is null`() {
        val req = SignInRequest(
            email = "email@example.com",
            password = "password"
        )

        `when`(userService.getByEmail(req.email)).thenReturn(savedUser)
        `when`(passwordEncoder.matches(req.password, savedUser.passwordDigest)).thenReturn(true)
        `when`(jwtService.generateToken(savedUser)).thenReturn(null)

        assertThrows(IllegalStateException::class.java) {
            authService.signIn(req)
        }
    }

    @Test
    fun `signUp() should throw an exception if the token is null`() {
        val req = SignUpRequest(
            firstName = "firstName",
            lastName = "lastName",
            email = "email@example.com",
            password = "password"
        )

        `when`(userService.create(req)).thenReturn(savedUser.id.toHexString())
        `when`(userService.getById(savedUser.id.toHexString())).thenReturn(savedUser)
        `when`(jwtService.generateToken(savedUser)).thenReturn(null)

        assertThrows(IllegalStateException::class.java) {
            authService.signUp(req)
        }
    }
}