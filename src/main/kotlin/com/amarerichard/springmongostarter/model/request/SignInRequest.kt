package com.amarerichard.springmongostarter.model.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

class SignInRequest(
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email address")
    val email: String,
    @field:NotBlank(message = "Password is required")
    @field:Length(min = 8, max = 32, message = "Password length must be between 8 and 32 characters")
    val password: String,
)
