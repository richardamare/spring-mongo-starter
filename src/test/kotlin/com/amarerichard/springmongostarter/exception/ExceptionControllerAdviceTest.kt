package com.amarerichard.springmongostarter.exception

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 *
 * @project spring-mongo-starter
 * @author richardamare on 07.03.2023
 */
class ExceptionControllerAdviceTest {

    private lateinit var advice: ExceptionControllerAdvice

    @BeforeEach
    fun setUp() {
        advice = ExceptionControllerAdvice()
    }

    @Test
    fun `handleException() should handle any exception`() {
        val response = advice.handleException(Exception("error"))
        assertEquals(500, response.body?.code)
        assertEquals("error", response.body?.message)
    }

    @Test
    fun `handleNotFoundException() should handle ResourceNotFoundException`() {
        val response = advice.handleNotFoundException(ResourceNotFoundException("custom message"))
        assertEquals(404, response.body?.code)
        assertEquals("custom message", response.body?.message)
    }

    @Test
    fun `handleDuplicateException() should handle DuplicateResourceException`() {
        val response = advice.handleDuplicateException(DuplicateResourceException("custom message"))
        assertEquals(400, response.body?.code)
        assertEquals("custom message", response.body?.message)
    }

    @Test
    fun `handleIllegalArgumentException() should handle IllegalArgumentException`() {
        val response = advice.handleIllegalArgumentException(IllegalArgumentException("custom message"))
        assertEquals(400, response.body?.code)
        assertEquals("custom message", response.body?.message)
    }

    @Test
    fun `handleIllegalStateException() should handle IllegalStateException`() {
        val response = advice.handleIllegalStateException(IllegalStateException("custom message"))
        assertEquals(500, response.body?.code)
        assertEquals("custom message", response.body?.message)
    }
}