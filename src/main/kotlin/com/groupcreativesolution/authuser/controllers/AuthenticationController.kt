package com.groupcreativesolution.authuser.controllers

import com.fasterxml.jackson.annotation.JsonView
import com.groupcreativesolution.authuser.dtos.UserDto
import com.groupcreativesolution.authuser.dtos.UserView
import com.groupcreativesolution.authuser.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/auth")
class AuthenticationController @Autowired constructor(private val userService: UserService) {

    @PostMapping("/signup")
    fun registerUser(
        @RequestBody @Validated(UserView.RegistrationPost::class)
        @JsonView(UserView.RegistrationPost::class) user: UserDto
    ): ResponseEntity<Any> {
        if (userService.findByUsername(user.username).isPresent) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists")
        }

        if (userService.findByEmail(user.email ?: "").isPresent) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists")
        }
        val userModel = userService.saveUser(user)
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel)
    }
}