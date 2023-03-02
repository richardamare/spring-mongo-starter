package com.amarerichard.springmongostarter.exception

import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = org.springframework.http.HttpStatus.BAD_REQUEST)
class DuplicateResourceException(message: String) : RuntimeException(message)