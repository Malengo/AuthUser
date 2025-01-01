package com.groupcreativesolution.authuser.models

import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.persistence.*
import java.io.Serializable
import java.util.UUID
import kotlin.jvm.Transient

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "user_courses")
class UserCourseModel(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val user: UserModel? = null,

    @Column(nullable = false)
    val courseId: UUID? = null

): Serializable {
    companion object {
        @Transient
        private const val serialVersionUID = 1L
    }
}