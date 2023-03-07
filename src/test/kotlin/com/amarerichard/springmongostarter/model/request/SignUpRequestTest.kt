package com.amarerichard.springmongostarter.model.request

import jakarta.validation.Validation
import jakarta.validation.Validator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SignUpRequestTest {
    private lateinit var validator: Validator

    @BeforeEach
    fun setup() {
        val factory = Validation.buildDefaultValidatorFactory()
        validator = factory.validator
    }

    @Test
    fun `should set and get email, password, and names`() {
        val request = SignUpRequest("firstName", "lastName", "email", "password")
        assertEquals("email", request.email)
        assertEquals("password", request.password)
        assertEquals("firstName", request.firstName)
        assertEquals("lastName", request.lastName)
    }

    @Test
    fun `should throw exception when fields are empty`() {
        val req = SignUpRequest("", "", "", "")
        val violations = validator.validate(req)

        assertEquals(5, violations.size)
        assertTrue(violations.any { it.message == "Email is required" })
        assertTrue(violations.any { it.message == "Password is required" })
        assertTrue(violations.any { it.message == "Password length must be between 8 and 32 characters" })
        assertTrue(violations.any { it.message == "First name is required" })
        assertTrue(violations.any { it.message == "Last name is required" })
    }

    @Test
    fun `should throw exception when email is invalid`() {
        val req = SignUpRequest("firstName", "lastName", "email", "password")
        val violations = validator.validate(req)

        assertEquals(1, violations.size)
        assertTrue(violations.any { it.message == "Invalid email address" })
    }
}