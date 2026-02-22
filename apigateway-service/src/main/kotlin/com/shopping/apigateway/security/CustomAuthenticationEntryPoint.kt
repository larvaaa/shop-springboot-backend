package com.shopping.apigateway.security

import com.common.core.util.JwtAuthenticationException
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class CustomAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper
) : ServerAuthenticationEntryPoint {

    private val log = KotlinLogging.logger {}

    override fun commence(exchange: ServerWebExchange, ex: AuthenticationException?): Mono<Void> {
        log.info("인증실패로 authenticationEntryPoint에서 처리 시작")

        val response: ServerHttpResponse = exchange.response

        // 1. 상태 코드 설정 (401 Unauthorized)
        response.statusCode = HttpStatus.UNAUTHORIZED
        response.headers.contentType = MediaType.APPLICATION_JSON

        // 예외 타입에 따라 분기 처리
        val (errorCode, errorMessage) = when (ex) {
            // ★ 여기서 커스텀 예외를 잡아서 처리
            is JwtAuthenticationException -> {
                // ex.errorType에 접근 가능
                ex.errorType to ex.message
            }
            // 그 외 일반적인 인증 예외 처리
            else -> "AUTH_FAIL" to "인증에 실패했습니다."
        }

        log.info("인증실패 코드: $errorCode, 메시지: $errorMessage")

        // 2. 실패 사유 객체 생성 (원하는 포맷으로 커스텀)
        val errorResponse = mapOf(
            "code" to errorCode,
            "message" to errorMessage,
        )

        // 3. JSON 변환 및 응답 쓰기
        val bytes = objectMapper.writeValueAsBytes(errorResponse)
        val buffer: DataBuffer = response.bufferFactory().wrap(bytes)

        return response.writeWith(Mono.just(buffer))
    }
}