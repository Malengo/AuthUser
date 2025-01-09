package com.groupcreativesolution.authuser.dtos

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

class RestResponsePage<T> @JsonCreator constructor(
    @JsonProperty("content") content: List<T> = emptyList(),
    @JsonProperty("number") number: Int = 0,
    @JsonProperty("size") size: Int = 1,
    @JsonProperty("totalElements") totalElements: Long = 0L,
    @JsonProperty("pageable") pageable: JsonNode? = null,
    @JsonProperty("last") last: Boolean = false,
    @JsonProperty("totalPages") totalPages: Int = 0,
    @JsonProperty("sort") sort: JsonNode? = null,
    @JsonProperty("first") first: Boolean = false,
    @JsonProperty("numberOfElements") numberOfElements: Int = 0
) : PageImpl<T>(content, PageRequest.of(number, size), totalElements) {
    constructor(content: List<T>, pageable: PageRequest, total: Long) : this(
        content = content,
        number = pageable.pageNumber,
        size = pageable.pageSize,
        totalElements = total
    )

    constructor(content: List<T>) : this(content = content, pageable = null, sort = null)

    constructor() : this(content = emptyList())
}