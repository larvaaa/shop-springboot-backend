package com.shopping.apigateway.security

import com.common.core.util.JwtUtil
import io.jsonwebtoken.Claims
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AuthenticationManager(
    private val jwtUtil: JwtUtil // JWT 파싱 유틸리티 (아래 구현 참고)
) : ReactiveAuthenticationManager {

    private val log = KotlinLogging.logger {}

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val authToken = authentication.credentials.toString()

        var status = HttpStatus.OK
        var code = ""

        // 1. 토큰 유효성 검증
//            if (!jwtUtil.validateToken(authToken)) {
//                return Mono.empty()
//            }
        log.info("토큰 validation 시작")
        jwtUtil.validateTokenOrThrow(authToken)
        log.info("토큰 validation 성공")
        // 2. Claims 추출
        val claims: Claims = jwtUtil.getClaimsFromToken(authToken)
        log.info("사용자아이디 추출 => ${claims.subject}")
        // 3. 권한(Role) 처리 - 타입 캐스팅 안전하게 처리
        // claims["role"]이 List<String> 형태라고 가정
        @Suppress("UNCHECKED_CAST")
        val roles = claims["role"] as? List<String> ?: emptyList()

        val authorities = roles.map { SimpleGrantedAuthority(it) }

        // 4. 인증 객체 생성 및 반환
        return Mono.just(
            UsernamePasswordAuthenticationToken(
                claims.subject, // Principal (User ID)
                authToken,      // Credentials
                authorities     // Authorities
            )
        )

    }
}