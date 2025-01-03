package com.groupcreativesolution.authuser.repositories.specifications

import com.groupcreativesolution.authuser.enums.UserStatus
import com.groupcreativesolution.authuser.enums.UserType
import com.groupcreativesolution.authuser.models.UserCourseModel
import com.groupcreativesolution.authuser.models.UserModel
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Join
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification
import java.util.UUID

data class UserModelSpecification(
    val userStatus: UserStatus? = null,
    val userType: UserType? = null
) : Specification<UserModel> {

    override fun toPredicate(
        root: Root<UserModel>,
        query: CriteriaQuery<*>?,
        criteriaBuilder: CriteriaBuilder
    ): Predicate? {
        val predicate: MutableList<Predicate> = ArrayList()

        if (userStatus != null) {
            predicate.add(criteriaBuilder.equal(root.get<UserStatus>("userStatus"), userStatus))
        }

        if (userType != null) {
            predicate.add(criteriaBuilder.equal(root.get<UserType>("userType"), userType))
        }

        return criteriaBuilder.and(*predicate.toTypedArray())
    }

    fun findByCourseId(courseId: UUID): Specification<UserModel> {
        return Specification<UserModel> { root, query, criteriaBuilder ->
            query?.distinct(true)
            val userCourseJoin: Join<UserModel, UserCourseModel> = root.join("userCourses")
            criteriaBuilder.equal(userCourseJoin.get<UUID>("courseId"), courseId)
        }
    }

}