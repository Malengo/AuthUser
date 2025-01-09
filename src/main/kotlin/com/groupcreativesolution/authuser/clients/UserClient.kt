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
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestTemplate
import java.util.*

@Component
class UserClient(@Autowired private val restClient: RestClient) {
    fun getAllCourseByUser(userId: UUID, pageable: Pageable): Page<CourseDTO> {
        var searchResult: Page<CourseDTO>? = null
        val url = "/api/v1/courses?userId=$userId&page=${pageable.pageNumber}&size=${pageable.pageSize}&sort=${pageable.sort.toString().replace(": ", ",")}"

        try {
            val responseType = object : ParameterizedTypeReference<RestResponsePage<CourseDTO>>() {}
            searchResult = restClient.get()
                .uri(url)
                .retrieve()
                .body(responseType)
                searchResult?.let { return it }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Page.empty()
    }
}