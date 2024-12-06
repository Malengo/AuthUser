package com.groupcreativesolution.authuser.services

import com.groupcreativesolution.authuser.dtos.UserDto
import com.groupcreativesolution.authuser.models.UserModel
import com.groupcreativesolution.authuser.repositories.specifications.UserModelSpecification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface UserService {

    fun findAllUser(): List<UserModel>
    fun findById(userId: UUID): Optional<UserModel>
    fun deleteUser(user: UserModel)
    fun saveUser(userDto: UserDto): UserModel
    fun findByUsername(userName: String): Optional<UserModel>
    fun findByEmail(email: String): Optional<UserModel>
    fun updateUser(user: UserModel)
    fun findAllUserPageable(pageableDefault: Pageable, specification: UserModelSpecification): Page<UserModel>
}