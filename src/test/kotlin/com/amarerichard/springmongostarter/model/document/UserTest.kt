package com.amarerichard.springmongostarter.model.document

import org.assertj.core.api.Assertions.assertThat
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class UserTest {

    @Test
    fun `should set and get properties correctly`() {
        val id = ObjectId()
        val firstName = "John"
        val lastName = "Doe"
        val email = "john.doe@example.com"
        val passwordDigest = "password123"
        val createdAt = LocalDateTime.now().minusDays(1)
        val updatedAt = LocalDateTime.now()

        val user = User(id, firstName, lastName, email, passwordDigest, createdAt, updatedAt)

        assertThat(user.id).isEqualTo(id)
        assertThat(user.firstName).isEqualTo(firstName)
        assertThat(user.lastName).isEqualTo(lastName)
        assertThat(user.email).isEqualTo(email)
        assertThat(user.passwordDigest).isEqualTo(passwordDigest)
        assertThat(user.createdAt).isEqualTo(createdAt)
        assertThat(user.updatedAt).isEqualTo(updatedAt)
    }

    @Test
    fun `should implement UserDetails interface correctly`() {
        val user = User(
            firstName = "Jane",
            lastName = "Doe",
            email = "jane.doe@example.com",
            passwordDigest = "password123"
        )

        assertThat(user.authorities).isEmpty()
        assertThat(user.password).isEqualTo("password123")
        assertThat(user.username).isEqualTo("jane.doe@example.com")
        assertThat(user.isAccountNonExpired).isTrue
        assertThat(user.isAccountNonLocked).isTrue
        assertThat(user.isCredentialsNonExpired).isTrue
        assertThat(user.isEnabled).isTrue
    }

    @Test
    fun `should convert to DTO correctly`() {
        val id = ObjectId()
        val firstName = "John"
        val lastName = "Doe"
        val email = "johndoe@example.com"

        val user = User(id, firstName, lastName, email, "password123")
        val userDTO = user.toDTO()

        assertThat(userDTO.id).isEqualTo(id.toHexString())
        assertThat(userDTO.firstName).isEqualTo(firstName)
        assertThat(userDTO.lastName).isEqualTo(lastName)
        assertThat(userDTO.email).isEqualTo(email)
    }
}