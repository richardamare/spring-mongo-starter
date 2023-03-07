package com.amarerichard.springmongostarter.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {

    private fun handleResponse(r: ErrorResponse): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(r.code).body(r)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleNotFoundException(e: ResourceNotFoundException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            code = 404,
            message = e.message ?: "Resource not found"
        )
        return handleResponse(response)
    }

    @ExceptionHandler(DuplicateResourceException::class)
    fun handleDuplicateException(e: DuplicateResourceException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            code = 400,
            message = e.message ?: "Duplicate resource"
        )
        return handleResponse(response)
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(e: IllegalStateException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            code = 500,
            message = e.message ?: "Internal server error"
        )
        return handleResponse(response)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            code = 400,
            message = e.message ?: "Bad request"
        )
        return handleResponse(response)
    }

    // This is the default exception handler (fallback)
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            code = 500,
            message = e.message ?: "Internal server error"
        )
        return handleResponse(response)
    }
}