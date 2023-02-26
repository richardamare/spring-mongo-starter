package com.amarerichard.springmongostarter.model.response

import org.springframework.http.ResponseEntity

data class ErrorResponse(
    val code: Int,
    val message: String,
)

fun handleExceptionResponse(e: Exception): ResponseEntity<Any> {
    val response = when (e) {
        is IllegalArgumentException -> ErrorResponse(400, e.message ?: "Bad Request")
        is IllegalStateException -> ErrorResponse(400, e.message ?: "Bad Request")
        is NoSuchElementException -> ErrorResponse(404, e.message ?: "Not Found")
        is AccessDeniedException -> ErrorResponse(403, e.message ?: "Forbidden")
        else -> ErrorResponse(500, e.message ?: "Internal Server Error")
    }
    return ResponseEntity.status(response.code).body(response)
}