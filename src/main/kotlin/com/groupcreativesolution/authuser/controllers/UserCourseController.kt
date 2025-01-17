package com.groupcreativesolution.authuser.controllers

import com.groupcreativesolution.authuser.clients.CourseClient
import com.groupcreativesolution.authuser.dtos.CourseDTO
import com.groupcreativesolution.authuser.dtos.UserCourseDTO
import com.groupcreativesolution.authuser.models.UserCourseModel
import com.groupcreativesolution.authuser.models.UserModel
import com.groupcreativesolution.authuser.services.UserCourseModelService
import com.groupcreativesolution.authuser.services.UserService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
class UserCourseController(
    @Autowired private val userClient: CourseClient,
    @Autowired private val userService: UserService,
    @Autowired private val userCourseModelService: UserCourseModelService
) {

    @GetMapping("/users/{userId}/courses")
    fun getAllCourseByUser(
        @PathVariable("userId") userId: UUID,
        @PageableDefault(page = 0, size = 10, sort = ["courseId"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<Page<CourseDTO>> {
        return ResponseEntity.status(HttpStatus.OK).body(userClient.getAllCourseByUser(userId, pageable))
    }

    @PostMapping("/users/{userId}/courses/subscription")
    fun saveSubscriptionUserInCourse(
        @PathVariable("userId") userId: UUID,
        @RequestBody @Valid userCourseDTO: UserCourseDTO
    ): ResponseEntity<Any> {
        val user: UserModel = userService.findById(userId) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found")

        if (userCourseModelService.existsByUserAndCourseId(user, userCourseDTO.courseId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already subscribed to this course")
        }

        val userModel: UserCourseModel = userCourseModelService.save(user.convertToUserCourseModel(userCourseDTO.courseId))
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel)
    }
}