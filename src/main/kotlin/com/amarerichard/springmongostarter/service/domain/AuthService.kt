package com.amarerichard.springmongostarter.service.domain

import com.amarerichard.springmongostarter.model.request.SignInRequest
import com.amarerichard.springmongostarter.model.request.UserCreateRequest
import com.amarerichard.springmongostarter.model.response.AuthResponse

interface AuthService {
    fun signUp(req: UserCreateRequest): AuthResponse
    fun signIn(req: SignInRequest): AuthResponse
}