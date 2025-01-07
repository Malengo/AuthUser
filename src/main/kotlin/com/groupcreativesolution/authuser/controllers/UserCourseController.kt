package com.groupcreativesolution.authuser.controllers

import com.groupcreativesolution.authuser.clients.UserClient
import com.groupcreativesolution.authuser.dtos.CourseDTO
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
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
class UserCourseController(@Autowired private val userClient: UserClient) {

    @GetMapping("/users/{userId}/courses")
    fun getAllCourseByUser(
        @PathVariable("userId") userId: UUID,
        @PageableDefault(page = 0, size = 10, sort = ["courseId"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<Page<CourseDTO>> {
        return ResponseEntity.status(HttpStatus.OK).body(userClient.getAllCourseByUser(userId, pageable))
    }
}