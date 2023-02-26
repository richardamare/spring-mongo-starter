package com.amarerichard.springmongostarter.model.request

data class SignInRequest(
    val email: String,
    val password: String,
)
