package com.amarerichard.springmongostarter.service.domain

import com.amarerichard.springmongostarter.model.document.User
import com.amarerichard.springmongostarter.model.request.SignUpRequest

interface UserService {
    fun create(request: SignUpRequest): String
    fun getById(id: String): User
    fun getByEmail(email: String): User
    fun deleteById(id: String)
}