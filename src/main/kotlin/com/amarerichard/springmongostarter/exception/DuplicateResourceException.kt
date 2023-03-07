package com.amarerichard.springmongostarter.exception

import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = BAD_REQUEST)
class DuplicateResourceException(message: String) : RuntimeException(message)