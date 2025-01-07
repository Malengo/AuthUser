package com.groupcreativesolution.authuser.clients

import com.groupcreativesolution.authuser.dtos.CourseDTO
import com.groupcreativesolution.authuser.dtos.RestResponsePage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import java.util.*

@Component
class UserClient(@Autowired private val restTemplate: RestTemplate) {

    private final val baseUrl = "http://localhost:8085"
    fun getAllCourseByUser(userId: UUID, pageable: Pageable): Page<CourseDTO> {
        var searchResult: List<CourseDTO>? = null
        val url = "$baseUrl/api/v1/courses?userId=$userId&page=${pageable.pageNumber}&size=${pageable.pageSize}&sort=${pageable.sort.toString().replace(": ", ",")}"

        try {
            val responseType = object : ParameterizedTypeReference<RestResponsePage<CourseDTO>>() {}
            searchResult = restTemplate.exchange(url, HttpMethod.GET, null, responseType).body?.content
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return searchResult?.let { PageImpl(it, pageable, searchResult.size.toLong()) } ?: Page.empty()
    }
}