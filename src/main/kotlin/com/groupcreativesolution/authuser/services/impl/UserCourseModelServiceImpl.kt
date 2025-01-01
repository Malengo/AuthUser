package com.groupcreativesolution.authuser.services.impl

import com.groupcreativesolution.authuser.repositories.UserCourseRepository
import com.groupcreativesolution.authuser.services.UserCourseModelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserCourseModelServiceImpl(@Autowired private val userCourseRepository: UserCourseRepository) :
    UserCourseModelService {
}