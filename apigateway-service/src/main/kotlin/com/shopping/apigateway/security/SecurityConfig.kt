package com.shopping.apigateway.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    private val authenticationManager: AuthenticationManager,
    private val securityContextRepository: SecurityContextRepository,
    private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint
) {

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .cors { it.configurationSource(corsConfigurationSource()) }
            .csrf { it.disable() } // CSRF 비활성화
            .formLogin { it.disable() } // Form 로그인 비활성화
            .httpBasic { it.disable() } // Http Basic 인증 비활성화
            .authenticationManager(authenticationManager)
            .securityContextRepository(securityContextRepository)
            .authorizeExchange { exchanges ->
                exchanges.pathMatchers(HttpMethod.OPTIONS).permitAll()
                exchanges
                    .pathMatchers(
                        "/member-service/users",
                        "/member-service/login",
                        "/member-service/error",
                        "/member-service/duplicateCheck",
                        "/member-service/accessToken",
                        "/member-service/test/**"
                    ).permitAll() // 인증 없이 접근 허용
                    .anyExchange().authenticated() // 그 외 모든 요청 인증 필요
            }
            .exceptionHandling { handling ->
                // 인증 실패 시 401 에러 처리 (선택사항)
                handling.authenticationEntryPoint(customAuthenticationEntryPoint)
            }
            .build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            addAllowedOrigin("http://localhost:3000")
            addAllowedMethod("*")
            addAllowedHeader("*")
            allowCredentials = true
            maxAge = 3600L // Preflight 요청 캐싱 시간 (1시간)
        }

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}