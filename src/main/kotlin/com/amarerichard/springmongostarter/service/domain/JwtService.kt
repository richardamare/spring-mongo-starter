package com.amarerichard.springmongostarter.service.domain

import io.jsonwebtoken.Claims
import org.springframework.security.core.userdetails.UserDetails
import java.security.Key
import java.util.*
import kotlin.reflect.KFunction1

interface JwtService {
    fun extractUsername(token: String?): String?
    fun <T> extractClaim(token: String?, claimsResolver: KFunction1<Claims, T>): T
    fun generateToken(userDetails: UserDetails): String?
    fun generateToken(extraClaims: Map<String, Any>, userDetails: UserDetails): String?
    fun isTokenValid(token: String?, userDetails: UserDetails): Boolean
    fun isTokenExpired(token: String?): Boolean
    fun extractExpiration(token: String?): Date?
    fun extractAllClaims(token: String?): Claims
    fun getSignKey(): Key
}