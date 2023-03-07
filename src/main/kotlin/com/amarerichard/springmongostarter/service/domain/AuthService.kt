package com.amarerichard.springmongostarter.service.domain

import com.amarerichard.springmongostarter.model.dto.AuthDTO
import com.amarerichard.springmongostarter.model.request.SignInRequest
import com.amarerichard.springmongostarter.model.request.SignUpRequest

interface AuthService {
    fun signUp(req: SignUpRequest): AuthDTO
    fun signIn(req: SignInRequest): AuthDTO
}