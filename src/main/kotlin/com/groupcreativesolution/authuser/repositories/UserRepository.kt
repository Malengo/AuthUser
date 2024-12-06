package com.groupcreativesolution.authuser.repositories

import com.groupcreativesolution.authuser.models.UserModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.Optional
import java.util.UUID

interface UserRepository : JpaRepository<UserModel, UUID>, JpaSpecificationExecutor<UserModel> {

    fun findByUsername(username: String): Optional<UserModel>
    fun findByEmail(email: String): Optional<UserModel>

}