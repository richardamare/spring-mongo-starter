package com.amarerichard.springmongostarter.model.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 *
 * @project spring-mongo-starter
 * @author richardamare on 06.03.2023
 */
class UserDTOTest {
    @Test
    fun `should create a user`() {
        val user = UserDTO(
            id = "user_id",
            firstName = "first_name",
            lastName = "last_name",
            email = "email",
        )

        assertEquals("user_id", user.id)
        assertEquals("first_name", user.firstName)
        assertEquals("last_name", user.lastName)
        assertEquals("email", user.email)
    }
}