package com.common.security.security

import com.common.core.util.JwtUtil
import com.common.security.entity.Role
import io.jsonwebtoken.Claims
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.filter.OncePerRequestFilter

class CustomTokenAuthFilter(
    private val jwtUtil: JwtUtil
) : OncePerRequestFilter() {

    private val log = KotlinLogging.logger {}

    private val skipPaths = listOf(
        AntPathRequestMatcher("/login"),
        AntPathRequestMatcher("/error"),
        AntPathRequestMatcher("/duplicateCheck"),
        AntPathRequestMatcher("/accessToken"),
        AntPathRequestMatcher("/test/**"),
    )

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val servletPath = request.servletPath ?: ""
        log.info("Processing path: $servletPath")

        if (skipPaths.any { it.matches(request) } || request.method == "OPTIONS") {
            log.info("Skipping authentication for path: $servletPath")
            SecurityContextHolder.clearContext() // SecurityContext 초기화
            filterChain.doFilter(request, response)
            return
        }

//        val headerNames = request.headerNames
//        println(" 헤더 목록 ===========>")
//        while (headerNames.hasMoreElements()) {
//            println(headerNames.nextElement())
//        }

//        val cookies = request.cookies
//        println(" 쿠키 목록 ===========>")
//        for ((index, value) in cookies.withIndex()) {
//            println("cookie[${index}] = $value")
//        }

        val authorizationHeaderValue = request.getHeader("authorization") ?: throw NullPointerException("Authorization 헤더가 없습니다.")

        if(!authorizationHeaderValue.startsWith("Bearer ")) throw IllegalStateException("Bearer 토큰 형식이 맞지 않습니다.")

        val accessToken: String = authorizationHeaderValue.substring(7)
        // log.info("accessToken = $accessToken")

        jwtUtil.validateToken(accessToken)

        val claim: Claims = jwtUtil.getClaimsFromToken(accessToken)

        // log.info("sub = ${claim.subject}")
        val memberId: Long = claim.subject.toLong()

        if(claim.get("roles") !is List<*>?) throw IllegalStateException("권한의 타입이 맞지 않습니다.(리스트 필요)")
        val roles: List<String> = claim.get("roles") as List<String>
        val convertedRoles = roles.map { it -> Role(null, it) }

        val context: SecurityContext = SecurityContextHolder.createEmptyContext()
        val authentication: Authentication = UsernamePasswordAuthenticationToken(memberId, null, convertedRoles)

        context.authentication = authentication
        SecurityContextHolder.setContext(context)

        filterChain.doFilter(request, response)

    }
}