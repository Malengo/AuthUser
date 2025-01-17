package com.groupcreativesolution.authuser.services.impl

import com.groupcreativesolution.authuser.models.UserCourseModel
import com.groupcreativesolution.authuser.models.UserModel
import com.groupcreativesolution.authuser.repositories.UserCourseRepository
import com.groupcreativesolution.authuser.services.UserCourseModelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserCourseModelServiceImpl(@Autowired private val userCourseRepository: UserCourseRepository) :
    UserCourseModelService {
    override fun existsByUserAndCourseId(user: UserModel, courseId: UUID): Boolean {
        return userCourseRepository.existsByUserAndCourseId(user, courseId)
    }

    override fun save(convertToUserCourseModel: UserCourseModel): UserCourseModel {
        return userCourseRepository.save(convertToUserCourseModel)
    }

}