package com.amarerichard.springmongostarter.model.request

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SignInRequestTest {
    @Test
    fun `should include email and password`() {
        val request = SignInRequest("email", "password")
        assertEquals("email", request.email)
        assertEquals("password", request.password)
    }
}