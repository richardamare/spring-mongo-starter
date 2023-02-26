package com.amarerichard.springmongostarter.service.data

import com.amarerichard.springmongostarter.service.domain.JwtService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import kotlin.reflect.KFunction1

@Service
class JwtServiceImpl : JwtService {

    override fun extractUsername(token: String?): String? {
        return extractClaim(token, Claims::getSubject)
    }

    override fun <T> extractClaim(token: String?, claimsResolver: KFunction1<Claims, T>): T {
        val claims = extractAllClaims(token)
        return claimsResolver.invoke(claims)
    }

    override fun generateToken(userDetails: UserDetails): String? {
        return generateToken(hashMapOf(), userDetails)
    }

    override fun generateToken(extraClaims: Map<String, Any>, userDetails: UserDetails): String? {
        val expiresAt = Date(System.currentTimeMillis() + 1000 * 60 * 24)
        val issuedAt = Date(System.currentTimeMillis())
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.username)
            .setIssuedAt(issuedAt)
            .setExpiration(expiresAt)
            .signWith(getSignKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    override fun isTokenValid(token: String?, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return (username.equals(userDetails.username)) && !isTokenExpired(token);
    }

    override fun isTokenExpired(token: String?): Boolean {
        return extractExpiration(token)?.before(Date()) ?: false
    }

    override fun extractExpiration(token: String?): Date? {
        return extractClaim(token, Claims::getExpiration)
    }

    override fun extractAllClaims(token: String?): Claims {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignKey())
            .build()
            .parseClaimsJws(token)
            .body
    }

    override fun getSignKey(): Key {
        val keyBytes = Decoders.BASE64.decode(JWT_SECRET)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    companion object {
        private const val JWT_SECRET: String = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970"
    }
}