package com.amarerichard.springmongostarter.model.response

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AuthResponseTest {
    @Test
    fun `should include token`() {
        val response = AuthResponse("token")
        assertEquals("token", response.token)
    }
}