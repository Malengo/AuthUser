package com.groupcreativesolution.authuser.services.impl

import com.groupcreativesolution.authuser.dtos.UserDto
import com.groupcreativesolution.authuser.models.UserModel
import com.groupcreativesolution.authuser.repositories.UserRepository
import com.groupcreativesolution.authuser.repositories.specifications.UserModelSpecification
import com.groupcreativesolution.authuser.services.UserService
import org.apache.catalina.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl @Autowired constructor(private val userRepository: UserRepository): UserService {

    override fun findAllUser(): List<UserModel> {
        return userRepository.findAll()
    }

    override fun findById(userId: UUID): Optional<UserModel> {
        return userRepository.findById(userId)
    }

    override fun deleteUser(user: UserModel) {
        userRepository.delete(user)
    }

    override fun saveUser(userDto: UserDto): UserModel {
        val userModel = UserDto.fromDto(userDto)
        userRepository.save(userModel)
        return userModel
    }

    override fun findByUsername(userName: String): Optional<UserModel> {
        return userRepository.findByUsername(userName)
    }

    override fun findByEmail(email: String): Optional<UserModel> {
        return userRepository.findByEmail(email)
    }

    override fun updateUser(user: UserModel) {
        userRepository.save(user)
    }

    override fun findAllUserPageable(pageableDefault: Pageable, specification: Specification<UserModel>): Page<UserModel> {
        return userRepository.findAll(specification, pageableDefault)
    }
}