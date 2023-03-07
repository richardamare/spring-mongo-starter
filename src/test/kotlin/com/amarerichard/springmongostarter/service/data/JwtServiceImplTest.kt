package com.amarerichard.springmongostarter.service.data

import com.amarerichard.springmongostarter.model.document.User
import com.mongodb.assertions.Assertions.assertNotNull
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

/**
 *
 * @project spring-mongo-starter
 * @author richardamare on 07.03.2023
 */
@ExtendWith(MockitoExtension::class)
class JwtServiceImplTest {

    lateinit var jwtService: JwtServiceImpl

    private lateinit var savedUser: User

    @BeforeEach
    fun setUp() {
        jwtService = JwtServiceImpl()
        jwtService.secret = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970"

        savedUser = User(
            id = ObjectId(),
            firstName = "firstName",
            lastName = "lastName",
            email = "email@example.com",
            passwordDigest = "password_digest"
        )
    }

    @Test
    fun `generate() should generate a token`() {
        val token = jwtService.generateToken(savedUser)
        assertNotNull(token)
    }

    @Test
    fun `extractAllClaims() should extract all claims`() {
        val token = jwtService.generateToken(savedUser)
        assertNotNull(token)

        val claims = jwtService.extractAllClaims(token)
        assertNotNull(claims)

        assertEquals(savedUser.email, claims.subject)
    }

    @Test
    fun `generate() should generate a token with custom claims`() {
        val token = jwtService.generateToken(mapOf("custom" to "claim"), savedUser)
        assertNotNull(token)

        val claims = jwtService.extractAllClaims(token)
        assertNotNull(claims)
        assertEquals("claim", claims["custom"])
    }

    @Test
    fun `extractUsername() should extract username`() {
        val token = jwtService.generateToken(mapOf("custom" to "claim"), savedUser)
        assertNotNull(token)

        val username = jwtService.extractUsername(token)
        assertNotNull(username)

        assertEquals(savedUser.email, username)
    }

    @Test
    fun `isTokenExpired() should return true if token is expired`() {
        val token = jwtService.generateToken(mapOf("custom" to "claim"), savedUser)
        assertNotNull(token)

        val isExpired = jwtService.isTokenExpired(token)
        assertNotNull(isExpired)

        assertEquals(false, isExpired)
    }

    @Test
    fun `extractExpiration() should extract expiration`() {
        val token = jwtService.generateToken(mapOf("custom" to "claim"), savedUser)
        assertNotNull(token)

        val expiration = jwtService.extractExpiration(token)
        assertNotNull(expiration)
    }

    @Test
    fun `isTokenValid() should return true if token is valid`() {
        val token = jwtService.generateToken(mapOf("custom" to "claim"), savedUser)
        assertNotNull(token)

        val isValid = jwtService.isTokenValid(token, savedUser)
        assertNotNull(isValid)

        assertEquals(true, isValid)
    }

    @Test
    fun `isTokenValid() should return false if token is invalid`() {
        val token = jwtService.generateToken(mapOf("custom" to "claim"), savedUser)
        assertNotNull(token)

        savedUser.email = "random@email.com"

        val isValid = jwtService.isTokenValid(token, savedUser)

        assertEquals(false, isValid)
    }
}