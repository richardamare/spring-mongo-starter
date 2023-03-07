package com.amarerichard.springmongostarter.model.document

import com.amarerichard.springmongostarter.model.dto.UserDTO
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
    val id: ObjectId = ObjectId(),
    var firstName: String,
    var lastName: String,
    @Indexed(unique = true)
    var email: String,
    var passwordDigest: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),
) : UserDetails {
    override fun getAuthorities() = emptyList<GrantedAuthority>()

    override fun getPassword() = passwordDigest

    override fun getUsername() = email

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true

    fun toDTO() = UserDTO(
        id = id.toHexString(),
        firstName = firstName,
        lastName = lastName,
        email = email,
    )
}