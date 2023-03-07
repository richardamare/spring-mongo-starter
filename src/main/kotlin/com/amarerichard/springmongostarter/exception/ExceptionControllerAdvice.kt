package com.amarerichard.springmongostarter.exception

import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
@Slf4j
class ExceptionControllerAdvice : ResponseEntityExceptionHandler() {

    override fun handleExceptionInternal(
        ex: java.lang.Exception,
        body: Any?,
        headers: HttpHeaders,
        statusCode: HttpStatusCode,
        request: WebRequest): ResponseEntity<Any>? {
        logger.error(ex.message, ex)
        return super.handleExceptionInternal(ex, body, headers, statusCode, request)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun notFoundException(e: ResourceNotFoundException, request: WebRequest): ResponseEntity<Any>? {
        val body = ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.message ?: "Resource not found")
        return handleExceptionInternal(e, body, HttpHeaders(), body.statusCode, request)
    }

    @ExceptionHandler(DuplicateResourceException::class)
    fun duplicateException(e: DuplicateResourceException, request: WebRequest): ResponseEntity<Any>? {
        val body = ErrorResponse.create(e, HttpStatus.CONFLICT, e.message ?: "Duplicate resource")
        return handleExceptionInternal(e, body, HttpHeaders(), body.statusCode, request)
    }

    @ExceptionHandler(IllegalStateException::class)
    fun illegalStateException(e: IllegalStateException, request: WebRequest): ResponseEntity<Any>? {
        val body = ErrorResponse.create(e, HttpStatus.INTERNAL_SERVER_ERROR, e.message ?: "Internal server error")
        return handleExceptionInternal(e, body, HttpHeaders(), body.statusCode, request)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgumentException(e: IllegalArgumentException, request: WebRequest): ResponseEntity<Any>? {
        val body = ErrorResponse.create(e, HttpStatus.BAD_REQUEST, e.message ?: "Bad request")
        return handleExceptionInternal(e, body, HttpHeaders(), body.statusCode, request)
    }

    @ExceptionHandler(Exception::class)
    fun fallbackException(e: Exception, request: WebRequest): ResponseEntity<Any>? {
        val body = ErrorResponse.create(e, HttpStatus.INTERNAL_SERVER_ERROR, e.message ?: "Internal server error")
        return handleExceptionInternal(e, body, HttpHeaders(), body.statusCode, request)
    }
}