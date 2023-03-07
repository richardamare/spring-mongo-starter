package com.amarerichard.springmongostarter.model.request

import jakarta.validation.Validation
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SignInRequestTest {

    private lateinit var validator: Validator

    @BeforeEach
    fun setup() {
        val factory = Validation.buildDefaultValidatorFactory()
        validator = factory.validator
    }

    @Test
    fun `should set and get email and password`() {
        val request = SignInRequest("email", "password")
        assertEquals("email", request.email)
        assertEquals("password", request.password)
    }

    @Test
    fun `should throw exception when email is blank`() {
        // validate email using jakarta validation
        val req = SignInRequest("", "password")
        val violations = validator.validate(req)
        assertEquals(1, violations.size)
        assertTrue(violations.any { it.message == "Email is required" })
    }

    @Test
    fun `should throw exception when password is blank`() {
        // validate password using jakarta validation
        val req = SignInRequest("email@email.com", "")
        val violations = validator.validate(req)
        assertEquals(2, violations.size)
        assertTrue(violations.any { it.message == "Password is required" })
        assertTrue(violations.any { it.message == "Password length must be between 8 and 32 characters" })
    }

    @Test
    fun `should throw exceptions when email and password are blank`() {
        val req = SignInRequest("", "")
        val violations = validator.validate(req)
        assertEquals(3, violations.size)
        assertTrue(violations.any { it.message == "Email is required" })
        assertTrue(violations.any { it.message == "Password is required" })
        assertTrue(violations.any { it.message == "Password length must be between 8 and 32 characters" })
    }

    @Test
    fun `should throw exception when email is invalid`() {
        // validate email using jakarta validation
        val req = SignInRequest("email", "password")
        val violations = validator.validate(req)
        assertEquals(1, violations.size)
        assertTrue(violations.any { it.message == "Invalid email address" })
    }

    @Test
    fun `should throw exception when password is invalid`() {
        // validate password using jakarta validation
        val req = SignInRequest("email@email.com", "p")
        val violations = validator.validate(req)
        assertEquals(1, violations.size)
        assertTrue(violations.any { it.message == "Password length must be between 8 and 32 characters" })
    }
}