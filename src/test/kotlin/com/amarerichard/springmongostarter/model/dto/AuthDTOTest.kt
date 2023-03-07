package com.amarerichard.springmongostarter.model.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AuthDTOTest {
    @Test
    fun `should include token`() {
        val response = AuthDTO("token")
        assertEquals("token", response.token)
    }
}