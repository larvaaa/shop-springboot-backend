package com.shopping.apigateway.security

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class SecurityContextRepository(
    private val authenticationManager: AuthenticationManager
) : ServerSecurityContextRepository {

    override fun save(exchange: ServerWebExchange, context: SecurityContext): Mono<Void> {
        // Stateless 방식이므로 Context를 세션에 저장하지 않음
        return Mono.error(UnsupportedOperationException("Not supported yet."))
    }

    override fun load(exchange: ServerWebExchange): Mono<SecurityContext> {
        return Mono.justOrEmpty(exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION))
            .filter { it.startsWith("Bearer ") }
            .map { it.substring(7) } // "Bearer " 제거
            .flatMap { authToken ->
                val auth = UsernamePasswordAuthenticationToken(authToken, authToken)
                authenticationManager.authenticate(auth)
                    .map { SecurityContextImpl(it) }
            }
    }
}