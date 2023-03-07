package com.amarerichard.springmongostarter.controller

import com.amarerichard.springmongostarter.model.document.User
import com.amarerichard.springmongostarter.service.domain.UserService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 *
 * @project spring-mongo-starter
 * @author richardamare on 07.03.2023
 */
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userService: UserService

    @Test
    fun `me() should return 200 status`() {
        val savedUser = User(
            firstName = "test",
            lastName = "user",
            email = "email@example.com",
            passwordDigest = "testpassword"
        )

        `when`(userService.getByEmail(savedUser.email)).thenReturn(savedUser)

        mockMvc.perform(
            get("/api/v1/users/me")
                .with(user(savedUser))
        )
            .andExpect(status().isOk)
    }
}