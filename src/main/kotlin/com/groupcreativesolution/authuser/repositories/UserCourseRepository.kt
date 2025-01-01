package com.groupcreativesolution.authuser.repositories

import com.groupcreativesolution.authuser.models.UserCourseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserCourseRepository: JpaRepository<UserCourseModel, UUID> {
}