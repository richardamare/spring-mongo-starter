package com.amarerichard.springmongostarter.controller

import com.amarerichard.springmongostarter.exception.DuplicateResourceException
import com.amarerichard.springmongostarter.exception.ResourceNotFoundException
import com.amarerichard.springmongostarter.model.document.User
import com.amarerichard.springmongostarter.model.dto.AuthDTO
import com.amarerichard.springmongostarter.model.request.SignInRequest
import com.amarerichard.springmongostarter.model.request.SignUpRequest
import com.amarerichard.springmongostarter.service.domain.AuthService
import com.amarerichard.springmongostarter.service.domain.JwtService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 *
 * @project spring-mongo-starter
 * @author richardamare on 07.03.2023
 */
@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var authService: AuthService

    @MockBean
    private lateinit var jwtService: JwtService

    @Test
    fun `sign-up endpoint should return 200`() {
        val req = SignUpRequest(
            firstName = "test",
            lastName = "user",
            email = "testuser@example.com",
            password = "testpassword"
        )

        val res = AuthDTO(
            token = "testtoken"
        )

        `when`(authService.signUp(req)).thenReturn(res)

        val result = mockMvc.perform(
            post("/api/v1/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
        )
            .andExpect(status().isOk)
            .andReturn()

        val response = objectMapper.readValue(result.response.contentAsString, AuthDTO::class.java)

        assertEquals(res.token, response.token)
    }

    @Test
    fun `sign-up endpoint should return 400 when user already exists`() {
        val req = SignUpRequest(
            firstName = "test",
            lastName = "user",
            email = "testuser@example.com",
            password = "testpassword"
        )

        `when`(authService.signUp(req)).thenThrow(DuplicateResourceException("User already exists"))

        mockMvc.perform(
            post("/api/v1/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
        )
            .andExpect {
                status().isBadRequest
                jsonPath("\$.code").value(400)
                jsonPath("\$.message").value("User already exists")
            }
            .andReturn()
    }

    @Test
    fun `sign-up endpoint should return 500 when token is null`() {
        val req = SignUpRequest(
            firstName = "test",
            lastName = "user",
            email = "testuser@example.com",
            password = "testpassword"
        )

        `when`(
            jwtService.generateToken(
                User(
                    firstName = req.firstName,
                    lastName = req.lastName,
                    email = req.email,
                    passwordDigest = req.password
                )
            )
        ).thenReturn(null)

        mockMvc.perform(
            post("/api/v1/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
        )
            .andExpect {
                status().isInternalServerError
                jsonPath("\$.code").value(500)
                jsonPath("\$.message").value("Internal server error")
            }
            .andReturn()
    }

    @Test
    fun `sign-in endpoint should return 200`() {
        val req = SignInRequest(
            email = "email@example.com",
            password = "password"
        )

        `when`(authService.signIn(req)).thenReturn(AuthDTO("token"))

        mockMvc.perform(
            post("/api/v1/auth/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
        )
            .andExpect {
                status().isOk
                jsonPath("\$.token").value("token")
            }
            .andReturn()

    }

    @Test
    fun `sign-in endpoint should return 400 when user does not exist`() {
        val req = SignInRequest(
            email = "email@example.com",
            password = "password"
        )

        `when`(authService.signIn(req)).thenThrow(ResourceNotFoundException("User not found"))

        mockMvc.perform(
            post("/api/v1/auth/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
        )
            .andExpect {
                status().isBadRequest
                jsonPath("\$.code").value(404)
                jsonPath("\$.message").value("User not found")
            }
            .andReturn()

    }

    @Test
    fun `sign-in endpoint should return 400 when password is incorrect`() {
        val req = SignInRequest(
            email = "email@example.com",
            password = "password"
        )

        `when`(authService.signIn(req)).thenThrow(IllegalArgumentException("Password is incorrect"))

        mockMvc.perform(
            post("/api/v1/auth/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
        )
            .andExpect {
                status().isBadRequest
                jsonPath("\$.code").value(400)
                jsonPath("\$.message").value("Password is incorrect")
            }
            .andReturn()
    }
}