package com.groupcreativesolution.authuser.dtos

import jakarta.validation.constraints.NotNull
import java.util.*

data class UserCourseDTO(
    @field:NotNull
    val courseId: UUID,
    val userId: UUID?
)
