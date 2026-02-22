package com.common.security.security

import com.common.core.util.JwtUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    val jwtUtil: JwtUtil
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        val allowedPaths = arrayOf(
            "users",
            "/login",
            "/error",
            "/duplicateCheck",
            "/accessToken",
            "/test/**"
        )

        http {
//            securityMatcher("/auth/**")
            csrf { disable() }
            formLogin { disable() }
            httpBasic { disable() }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            authorizeHttpRequests {
                allowedPaths.forEach { authorize(it, permitAll) }
                authorize(HttpMethod.OPTIONS, "/**", permitAll)
//                authorize("/login", permitAll)
//                authorize("/admin", hasAuthority("ADMIN"))
                authorize(anyRequest, authenticated)
            }

//            exceptionHandling {
//                it.accessDeniedHandler { request, response, accessDeniedException ->
//                    response.status = HttpServletResponse.SC_FORBIDDEN
//                    response.contentType = MediaType.APPLICATION_JSON_VALUE
//                    response.writer.write(
//                        """{"error": "Access denied", "message": "${accessDeniedException.message}", "path": "${request.servletPath}"}"""
//                    )
//                }
//            }
            // 커스텀 필터를 추가
            http.addFilterBefore(CustomTokenAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter::class.java)
        }

        return http.build()
    }
}