package com.groupcreativesolution.authuser.services

import com.groupcreativesolution.authuser.models.UserCourseModel
import com.groupcreativesolution.authuser.models.UserModel
import java.util.*

interface UserCourseModelService {
    fun existsByUserAndCourseId(user: UserModel, courseId: UUID): Boolean
    fun save(convertToUserCourseModel: UserCourseModel): UserCourseModel
}