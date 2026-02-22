package com.shopping.apigateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication(
    scanBasePackages = [
        "com.shopping.apigateway",  // 1. 현재 서비스 패키지
        "com.common.core",
    ]
)
@EnableDiscoveryClient
class ApigatewayApplication {

}

fun main(args: Array<String>) {
    runApplication<ApigatewayApplication>(*args)
}
