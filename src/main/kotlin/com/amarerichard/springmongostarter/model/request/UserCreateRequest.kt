package com.amarerichard.springmongostarter.model.request

data class UserCreateRequest(
    var name: String,
    var email: String,
    var password: String
)
