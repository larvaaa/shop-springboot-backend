package com.shopping.apigateway.filter

import mu.KotlinLogging
import org.springframework.core.Ordered
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class RequestLoggingWebFilter : WebFilter, Ordered {

    private val log = KotlinLogging.logger {}

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        // WebFilter 단계에서는 아직 Gateway가 라우팅을 결정하기 전일 수 있어
        // GATEWAY_ORIGINAL_REQUEST_URL_ATTR 속성이 없을 수 있습니다.
        // 들어온 날것의 요청 URI를 찍는 것이 가장 정확합니다.

        val request = exchange.request
        val method = request.method
        val uri = request.uri

        log.info(">>>>>>>>>>>>>>>> request: [$method] $uri")

        return chain.filter(exchange)
    }

    override fun getOrder(): Int {
        // SecurityFilter보다 먼저 실행되도록 최우선 순위 설정
        return Ordered.HIGHEST_PRECEDENCE
    }
}