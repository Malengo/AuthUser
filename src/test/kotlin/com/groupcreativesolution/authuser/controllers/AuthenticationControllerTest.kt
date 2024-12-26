package com.groupcreativesolution.authuser.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.groupcreativesolution.authuser.dtos.UserDto
import com.groupcreativesolution.authuser.services.UserService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.util.*

@WebMvcTest
class AuthenticationControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var userService: UserService

    @Test
    fun `should return 201 when user is created`() {
        // given
        val userRequest = UserDto(
            username = "user",
            email = "test@test.com"
        )

        val userResponse = UserDto.fromDto(userRequest)

        //when
        every { userService.saveUser(any()) } returns  userResponse
        every { userService.findByUsername(any()) } returns Optional.empty()
        every { userService.findByEmail(any()) } returns Optional.empty()

        //then
        mockMvc.post("/auth/signup") {
            contentType = MediaType.APPLICATION_JSON
            content = ObjectMapper().writeValueAsString(userRequest)
        }.andExpect {
            status { isCreated() }
            content { json(ObjectMapper().writeValueAsString(userResponse)) }
        }

    }

    @Test
    fun `should return 409 when username already exists`() {
        //given
        val userRequest = UserDto(
            username = "user",
            email = "test@test.com"
        )
        val userResponse = UserDto.fromDto(userRequest)

        every { userService.findByUsername(any()) } returns Optional.of(userResponse)

        //when
        mockMvc.post("/auth/signup") {
            contentType = MediaType.APPLICATION_JSON
            content = ObjectMapper().writeValueAsString(userRequest)
        }.andExpect {
            status { isConflict() }
            content { string("Username already exists") }
        }
    }

    @Test
    fun `should return 409 when email already exists`() {
        //given
        val userRequest = UserDto(
            username = "user",
            email = "test@test.com"
        )
        val userResponse = UserDto.fromDto(userRequest)

        every { userService.findByUsername(any()) } returns Optional.empty()
        every { userService.findByEmail(any()) } returns Optional.of(userResponse)

        //when
        mockMvc.post("/auth/signup") {
            contentType = MediaType.APPLICATION_JSON
            content = ObjectMapper().writeValueAsString(userRequest)
        }.andExpect {
            status { isConflict() }
            content { string("Email already exists") }
        }
    }
}