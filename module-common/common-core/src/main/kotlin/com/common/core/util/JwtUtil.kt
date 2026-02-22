package com.common.core.util

import io.jsonwebtoken.*
import io.jsonwebtoken.security.SignatureException
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Component
class JwtUtil {

    private val log = KotlinLogging.logger {}

    @Value("\${jwt.secret-key}")
    lateinit private var SECRET_KEY: String

    @Value("\${jwt.access-token-expiration-time}")
    lateinit var ACCESS_TOKEN_EXPIRATION_TIME: String

    @Value("\${jwt.refresh-token-expiration-time}")
    lateinit var REFRESH_TOKEN_EXPIRATION_TIME: String

    fun generateAccessToken(memberId: String, roles: List<String>?): String {
        val key = SecretKeySpec(SECRET_KEY.toByteArray(), SignatureAlgorithm.HS256.jcaName)
        val now = Date()
        val expirationDate = Date(now.time + ACCESS_TOKEN_EXPIRATION_TIME.toLong())

        return Jwts.builder()
            .claim("roles", roles)
            .setSubject(memberId)
            .setIssuedAt(now)
            .setExpiration(expirationDate)
            .signWith(key)
            .compact()
    }

    fun generateRefreshToken(memberId: String): String {
        val key = SecretKeySpec(SECRET_KEY.toByteArray(), SignatureAlgorithm.HS256.jcaName)
        val now = Date()
        val expirationDate = Date(now.time + REFRESH_TOKEN_EXPIRATION_TIME.toLong())

        return Jwts.builder()
            .setSubject(memberId)
            .setIssuedAt(now)
            .setExpiration(expirationDate)
            .signWith(key)
            .compact()
    }

    // 단순히 true/false를 리턴하는게 아니라 정확한 실패이유를 전달
    fun validateToken(token: String?): JwtResult {
        val key = SecretKeySpec(SECRET_KEY.toByteArray(), SignatureAlgorithm.HS256.jcaName)

        return try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)

            JwtResult.Success
        } catch (e: SignatureException) {
            log.error("Invalid JWT signature: ${e.message}")
            JwtResult.Failure(JwtErrorType.INVALID_SIGNATURE, "Invalid JWT signature")
        } catch (e: MalformedJwtException) {
            log.error("Invalid JWT token format: ${e.message}")
            JwtResult.Failure(JwtErrorType.MALFORMED, "Invalid JWT token format")
        } catch (e: ExpiredJwtException) {
            log.error("JWT token is expired: ${e.message}")
            JwtResult.Failure(JwtErrorType.EXPIRED, "JWT token is expired")
        } catch (e: UnsupportedJwtException) {
            log.error("Unsupported JWT token: ${e.message}")
            JwtResult.Failure(JwtErrorType.UNSUPPORTED, "Unsupported JWT token")
        } catch (e: IllegalArgumentException) {
            log.error("JWT claims string is empty or invalid: ${e.message}")
            JwtResult.Failure(JwtErrorType.EMPTY, "JWT claims string is empty or invalid")
        } catch (e: Exception) {
            log.error("Unexpected error during JWT validation: ${e.message}")
            JwtResult.Failure(JwtErrorType.UNKNOWN, "Unexpected error during JWT validation")
        }
    }

    fun getClaimsFromToken(token: String?): Claims {
        val key = SecretKeySpec(SECRET_KEY.toByteArray(), SignatureAlgorithm.HS256.jcaName)
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body // 클레임 정보 반환
    }

    // 시큐리티 필터에서 사용, 실패시 예외를 던져서 AuthenticationEntryPoint에서 처리
    @Throws(JwtAuthenticationException::class)
    fun validateTokenOrThrow(token: String) {
        val result = validateToken(token)
        if (result is JwtResult.Failure) {
            throw JwtAuthenticationException(result.errorType, result.message)
        }
    }

}

