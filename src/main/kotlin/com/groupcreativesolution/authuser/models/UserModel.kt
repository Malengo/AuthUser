package com.groupcreativesolution.authuser.models

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.groupcreativesolution.authuser.enums.UserStatus
import com.groupcreativesolution.authuser.enums.UserType
import jakarta.persistence.*
import java.io.Serializable
import java.util.*
import kotlin.jvm.Transient

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "users")
class UserModel(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var userId: UUID? = null,

    @Column(nullable = false, unique = true, length = 50)
    var username: String? = null,

    @Column(nullable = false, length = 255)
    @JsonIgnore
    var password: String? = null,

    @Column(nullable = false, unique = true, length = 50)
    var email: String? = null,

    @Column(nullable = false, length = 150)
    var fullName: String? = null,

    @Column(length = 20)
    var phoneNumber: String? = null,

    @Column(length = 20)
    var cpf: String? = null,

    @Column()
    var image: String? = null,

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    var createdAt: String? = null,

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    var updatedAt: String? = null,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var userStatus: UserStatus? = null,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var userType: UserType? = null

): Serializable {
    companion object {
        @Transient
        const val serialVersionUID = 1L
    }
}