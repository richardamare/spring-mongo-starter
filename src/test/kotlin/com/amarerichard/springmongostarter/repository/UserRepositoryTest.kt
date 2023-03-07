package com.amarerichard.springmongostarter.repository

import com.amarerichard.springmongostarter.model.document.User
import org.assertj.core.api.Assertions.assertThat
import org.bson.types.ObjectId
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import java.util.*

@DataMongoTest
class UserRepositoryTest {
    @Autowired
    private lateinit var userRepository: UserRepository

    private val user = User(
        ObjectId.get(),
        "John",
        "Doe",
        "john.doe@example.com",
        "password123"
    )

    @BeforeEach
    fun setUp() {
        userRepository.save(user)
    }

    @AfterEach
    fun tearDown() {
        userRepository.deleteAll()
    }

    @Test
    fun `should find user by email`() {
        val foundUser: Optional<User> = userRepository.findByEmail(user.email)

        assertThat(foundUser.isPresent).isTrue
        assertThat(foundUser.get().id).isEqualTo(user.id)
        assertThat(foundUser.get().firstName).isEqualTo(user.firstName)
        assertThat(foundUser.get().lastName).isEqualTo(user.lastName)
        assertThat(foundUser.get().email).isEqualTo(user.email)
        assertThat(foundUser.get().passwordDigest).isEqualTo(user.passwordDigest)
    }

    @Test
    fun `should return empty optional if user is not found`() {
        val foundUser: Optional<User> = userRepository.findByEmail("nonexistent@example.com")

        assertThat(foundUser.isPresent).isFalse
    }
}