package com.amarerichard.springmongostarter.model.document

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime

@Document(collection = "users")
class User(
    @Id
    @JsonSerialize(using = ToStringSerializer::class)
    var id: ObjectId = ObjectId(),
    var name: String,
    @Indexed(unique = true)
    var email: String,
    @JsonIgnore
    var passwordDigest: String,
    @JsonIgnore
    var createdAt: LocalDateTime = LocalDateTime.now(),
    @JsonIgnore
    var updatedAt: LocalDateTime = LocalDateTime.now(),
) : UserDetails {
    @JsonIgnore
    override fun getAuthorities() = emptyList<GrantedAuthority>()

    @JsonIgnore
    override fun getPassword() = passwordDigest

    @JsonIgnore
    override fun getUsername() = email

    @JsonIgnore
    override fun isAccountNonExpired() = true

    @JsonIgnore
    override fun isAccountNonLocked() = true

    @JsonIgnore
    override fun isCredentialsNonExpired() = true

    @JsonIgnore
    override fun isEnabled() = true
}