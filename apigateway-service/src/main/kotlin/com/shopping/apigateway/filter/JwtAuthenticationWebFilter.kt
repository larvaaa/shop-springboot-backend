package com.shopping.apigateway.filter

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class JwtAuthenticationWebFilter(
    private val authenticationManager: ReactiveAuthenticationManager,
    private val customAuthenticationEntryPoint: ServerAuthenticationEntryPoint
) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val token = extractToken(exchange) ?: return chain.filter(exchange)

        val auth = UsernamePasswordAuthenticationToken(token, token)

        return authenticationManager.authenticate(auth)
            .flatMap { authentication ->
                val context = SecurityContextImpl(authentication)
                chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context)))
            }
            .onErrorResume(AuthenticationException::class.java) { ex ->
                // 여기서 직접 entryPoint 호출
                customAuthenticationEntryPoint.commence(exchange, ex)
            }
    }

    private fun extractToken(exchange: ServerWebExchange): String? {
        val header = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)
        return if (header != null && header.startsWith("Bearer ")) header.substring(7) else null
    }
}