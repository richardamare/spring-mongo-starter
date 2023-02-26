package com.amarerichard.springmongostarter.model.request

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserCreateRequestTest {
    @Test
    fun `should include email, password, and name`() {
        val request = UserCreateRequest("name", "email", "password")
        assertEquals("email", request.email)
        assertEquals("password", request.password)
        assertEquals("name", request.name)
    }
}