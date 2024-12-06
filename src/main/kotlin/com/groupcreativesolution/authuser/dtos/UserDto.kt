package com.groupcreativesolution.authuser.dtos

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonView
import com.groupcreativesolution.authuser.enums.UserStatus
import com.groupcreativesolution.authuser.enums.UserType
import com.groupcreativesolution.authuser.models.UserModel
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserDto (

    @field:NotBlank(groups = [UserView.RegistrationPost::class])
    @field:Size(min = 3, max = 50, groups = [UserView.RegistrationPost::class])
    @JsonView(UserView.RegistrationPost::class)
    val username: String,

    @field:NotBlank(groups = [UserView.RegistrationPost::class])
    @field:Email(groups = [UserView.RegistrationPost::class])
    @JsonView(UserView.RegistrationPost::class)
    val email: String? = null,

    @NotBlank(groups = [UserView.RegistrationPost::class, UserView.PasswordPut::class])
    @Size(min = 6, max = 25, groups = [UserView.RegistrationPost::class, UserView.PasswordPut::class])
    @JsonView(UserView.RegistrationPost::class, UserView.PasswordPut::class)
    val password: String? = null,

    @NotBlank(groups = [UserView.PasswordPut::class])
    @Size(min = 6, max = 25, groups = [UserView.PasswordPut::class])
    @JsonView(UserView.PasswordPut::class)
    val oldPassword: String? = null,

    @JsonView(UserView.RegistrationPost::class, UserView.UserPut::class)
    val fullName: String? = null,

    @JsonView(UserView.RegistrationPost::class, UserView.UserPut::class)
    val phoneNumber: String? = null,

    @JsonView(UserView.RegistrationPost::class, UserView.UserPut::class)
    val cpf: String? = null,

    @NotBlank(groups = [UserView.ImagePut::class])
    @JsonView(UserView.ImagePut::class)
    val imgUrl: String? = null,
) {
    companion object {
        private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        fun fromDto(userDto: UserDto): UserModel {
            val now = LocalDateTime.now(ZoneId.of("UTC")).format(formatter)
            return UserModel(
                username = userDto.username,
                password = userDto.password,
                email = userDto.email,
                fullName = userDto.fullName,
                phoneNumber = userDto.phoneNumber,
                cpf = userDto.cpf,
                image = userDto.imgUrl,
                createdAt = now,
                updatedAt = now,
                userStatus = UserStatus.ACTIVE,
                userType = UserType.STUDENT
            )
        }
    }
}

interface UserView {
    interface RegistrationPost {}
    interface UserPut {}
    interface PasswordPut {}
    interface ImagePut {}
}