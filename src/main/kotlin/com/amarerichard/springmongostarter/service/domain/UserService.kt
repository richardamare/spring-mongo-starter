package com.amarerichard.springmongostarter.service.domain

import com.amarerichard.springmongostarter.model.document.User
import com.amarerichard.springmongostarter.model.request.UserCreateRequest

interface UserService {
    fun create(request: UserCreateRequest): User
    fun getAll(): List<User>
    fun getById(id: String): User
    fun findByEmail(email: String): User
    fun deleteById(id: String)
}