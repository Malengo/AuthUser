package com.groupcreativesolution.authuser.dtos

import org.groupcreativesolution.course.enuns.CourseLevel
import org.groupcreativesolution.course.enuns.CourseStatus
import java.util.*

data class CourseDTO(
    val courseId: UUID,
    val courseName: String,
    val description: String,
    val createdAt: String,
    val updatedAt: String,
    val courseStatus: CourseStatus,
    val userInstructor: UUID,
    val courseLevel: CourseLevel,
    )
