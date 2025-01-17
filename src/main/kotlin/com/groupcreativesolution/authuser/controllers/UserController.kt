package com.groupcreativesolution.authuser.controllers

import com.fasterxml.jackson.annotation.JsonView
import com.groupcreativesolution.authuser.dtos.UserDto
import com.groupcreativesolution.authuser.dtos.UserView
import com.groupcreativesolution.authuser.models.UserModel
import com.groupcreativesolution.authuser.repositories.specifications.UserModelSpecification
import com.groupcreativesolution.authuser.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/users")
class UserController @Autowired constructor(private val userService: UserService) {

    @GetMapping
    fun getAllUser(@PageableDefault(page = 0, size = 10, sort = ["userId"], direction = Sort.Direction.ASC) pageable: Pageable,
                   specification: UserModelSpecification,
                   @RequestParam(value = "courseId", required = false) courseId: UUID?
    ): ResponseEntity<Page<UserModel>> {
        val users: Page<UserModel> = if (courseId != null) {
            userService.findAllUserPageable(pageable, specification.findByCourseId(courseId).and(specification))
        } else {
            userService.findAllUserPageable(pageable, specification)
        }
        return ResponseEntity.status(HttpStatus.OK).body(users)
    }

    @GetMapping("/{userId}")
    fun getOneUser(@PathVariable(value = "userId") userId: UUID): ResponseEntity<Any> {
        val user: UserModel = userService.findById(userId) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found")
        return ResponseEntity.status(HttpStatus.OK).body(user)
    }

    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable(value = "userId") userId: UUID): ResponseEntity<Any> {
        val user: UserModel = userService.findById(userId) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found")
        userService.deleteUser(user)
        return ResponseEntity.status(HttpStatus.OK).body("User delete success")
    }

    @PutMapping("/{userId}")
    fun updateUser(
        @PathVariable(value = "userId") userId: UUID,
        @RequestBody @Validated(UserView.UserPut::class)
        @JsonView(UserView.UserPut::class) userDTO: UserDto
    ): ResponseEntity<Any> {
        val user: UserModel =
            userService.findById(userId) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found")

        val userModel = user
        userModel.fullName = userDTO.fullName
        userModel.phoneNumber = userDTO.phoneNumber
        userModel.cpf = userDTO.cpf
        userModel.updatedAt = LocalDateTime.now(ZoneId.of("UTC")).toString()
        userService.updateUser(userModel)
        return ResponseEntity.status(HttpStatus.OK).body(userModel)
    }


    @PutMapping("/{userId}/password")
    fun updatePassword(
        @PathVariable(value = "userId") userId: UUID,
        @RequestBody
        @Validated(UserView.PasswordPut::class)
        @JsonView(UserView.PasswordPut::class) userDTO: UserDto
    ): ResponseEntity<Any> {
        val user: UserModel =
            userService.findById(userId) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found")
        if (!user.password.equals(userDTO.oldPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password is incorrect")
        } else {
            val userModel = user
            userModel.password = userDTO.password
            userModel.updatedAt = LocalDateTime.now(ZoneId.of("UTC")).toString()
            userService.updateUser(userModel)
            return ResponseEntity.status(HttpStatus.OK).body("Password updated")
        }
    }

    @PutMapping("/{userId}/image")
    fun updateImage(
        @PathVariable(value = "userId") userId: UUID,
        @RequestBody @Validated(UserView.ImagePut::class)
        @JsonView(UserView.ImagePut::class) userDTO: UserDto
    ): ResponseEntity<Any> {
        val user: UserModel =
            userService.findById(userId) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found")

        val userModel = user
        userModel.image = userDTO.imgUrl
        userModel.updatedAt = LocalDateTime.now(ZoneId.of("UTC")).toString()
        userService.updateUser(userModel)
        return ResponseEntity.status(HttpStatus.OK).body(userModel)
    }

}