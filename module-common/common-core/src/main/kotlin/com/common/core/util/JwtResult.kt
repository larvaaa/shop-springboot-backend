package com.common.core.util

import org.springframework.security.core.AuthenticationException

enum class TokenType {
    ACCESS, REFRESH
}

enum class JwtErrorType {
    EXPIRED, INVALID_SIGNATURE, MALFORMED, UNSUPPORTED, EMPTY, UNKNOWN
}

sealed interface JwtResult {

    data object Success : JwtResult // 싱글톤
    data class Failure(
        val tokenType: TokenType,
        val errorType: JwtErrorType,
        val message: String
    ) : JwtResult
}

class JwtAuthenticationException(
    val tokenType: TokenType,
    val errorType: JwtErrorType,
    override val message: String
) : AuthenticationException(message)