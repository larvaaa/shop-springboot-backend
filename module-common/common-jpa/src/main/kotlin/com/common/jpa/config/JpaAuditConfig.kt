package com.common.jpa.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@Configuration
@EnableJpaAuditing // 이게 있어야 날짜가 자동 주입됨
class JpaAuditConfig {

    @Bean
    fun auditorProvider(): AuditorAware<String> = AuditorAware {
        val context = SecurityContextHolder.getContext()
        val authentication = context.authentication
        val principal = authentication?.principal?.toString() ?: "anonymous"

        Optional.of(principal)
    }
}