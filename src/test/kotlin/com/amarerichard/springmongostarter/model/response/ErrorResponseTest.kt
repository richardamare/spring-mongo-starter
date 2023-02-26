package com.amarerichard.springmongostarter.model.response

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ErrorResponseTest {
    @Test
    fun `should include code and message`() {
        val response = ErrorResponse(400, "Bad Request")
        assertEquals(400, response.code)
        assertEquals("Bad Request", response.message)
    }
}