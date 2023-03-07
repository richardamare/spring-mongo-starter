package com.amarerichard.springmongostarter.exception

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.web.context.request.WebRequest

/**
 *
 * @project spring-mongo-starter
 * @author richardamare on 07.03.2023
 */
@ExtendWith(MockitoExtension::class)
class ExceptionControllerAdviceTest {

    private lateinit var advice: ExceptionControllerAdvice

    @Mock
    private lateinit var request: WebRequest

    @BeforeEach
    fun setUp() {
        advice = ExceptionControllerAdvice()
    }

    @Test
    fun `fallbackException() returns 500 status`() {
        val response = advice.fallbackException(Exception("error"), request)
        assertEquals(500, response?.statusCode?.value())
    }

    @Test
    fun `notFoundException() returns 404 status`() {
        val response = advice.notFoundException(ResourceNotFoundException("custom message"), request)
        assertEquals(404, response?.statusCode?.value())
    }

    @Test
    fun `duplicateException() returns 409 status`() {
        val response = advice.duplicateException(DuplicateResourceException("custom message"), request)
        assertEquals(409, response?.statusCode?.value())
    }

    @Test
    fun `illegalArgumentException() returns 400 status`() {
        val response = advice.illegalArgumentException(IllegalArgumentException("custom message"), request)
        assertEquals(400, response?.statusCode?.value())
    }

    @Test
    fun `illegalStateException() returns 500 status`() {
        val response = advice.illegalStateException(IllegalStateException("custom message"), request)
        assertEquals(500, response?.statusCode?.value())
    }
}