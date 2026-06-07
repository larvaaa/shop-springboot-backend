package com.common.core.dto

import com.common.core.util.JwtErrorType
import com.common.core.util.TokenType

open class AuthenticationResult(
    val tokenType: TokenType = TokenType.ACCESS,
    val errorType: JwtErrorType? = null,
    val message: String? = null,
)
