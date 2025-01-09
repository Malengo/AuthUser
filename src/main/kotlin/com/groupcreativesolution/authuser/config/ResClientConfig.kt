package com.groupcreativesolution.authuser.config

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class ResClientConfig {

    @Bean
    fun restClient(builder: RestClient.Builder): RestClient = builder
        .baseUrl("http://localhost:8085")
        .build()
}