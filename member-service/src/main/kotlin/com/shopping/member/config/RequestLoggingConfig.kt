package com.shopping.member.config

import jakarta.servlet.Filter
import jakarta.servlet.http.HttpServletRequest
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RequestLoggingConfig {

    private val log = KotlinLogging.logger {}

    @Bean
    fun urlLoggingFilter(): Filter {
        return Filter { request, response, chain ->
            val req = request as HttpServletRequest

            // 로그 포맷: Request: [GET] /api/home
            log.info(">>>>>>>>>>>>>>>> request uri: [${req.method}] ${req.requestURI}")

            chain.doFilter(request, response)
        }
    }
}