package com.shopping.member.controller

import com.common.core.util.JwtResult
import com.common.core.util.JwtUtil
import com.common.core.util.TokenType
import com.shopping.member.dto.MemberDto
import com.shopping.member.dto.Roles
import com.shopping.member.entity.Member
import com.shopping.member.security.CustomUserDetailService
import com.shopping.member.service.AuthorityService
import com.shopping.member.service.MemberService
import io.jsonwebtoken.Claims
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
class LoginController (
    private val authenticationManager: AuthenticationManager,
    val memberService: MemberService,
    val authorityService: AuthorityService,
    val passwordEncoder: PasswordEncoder,
    val jwtUtil: JwtUtil,
    @Value("\${cookie.domain}") val COOKIE_DOMAIN: String,
    @Value("\${cookie.secure}") val COOKIE_SECURE: Boolean,
    @Value("\${cookie.same-site}") val COOKIE_SAME_SITE: String
) {

    private val log = KotlinLogging.logger {}

    @PostMapping("/login")
    fun login(
        @RequestBody loginRequest: LoginRequest,
        response: HttpServletResponse
    ): ResponseEntity<LoginResponse> {

        try {

            val authenticationRequest: UsernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.loginId, loginRequest.loginPw)

            val authenticationResponse: Authentication = authenticationManager.authenticate(authenticationRequest)
    //        SecurityContextHolder.getContext().authentication = authenticationResponse // 인증 상태 저장

            val findUserDetails: CustomUserDetailService.CustomUserDetails = authenticationResponse.principal as CustomUserDetailService.CustomUserDetails
            val memberId: String = findUserDetails.id.toString()
            val roles: List<GrantedAuthority>? = findUserDetails.roles
            val memberName: String = findUserDetails.name

            val convertedRoles: List<String>?
            if(roles != null && roles.size > 0) {
                convertedRoles = roles.map { it -> it.authority }
            } else {
                convertedRoles = null
            }

            val accessToken: String = jwtUtil.generateAccessToken(memberId, convertedRoles)
            log.info("accessToken = {}", accessToken)
            val accessCookie: ResponseCookie = ResponseCookie.from("accessToken", accessToken)
                .domain(COOKIE_DOMAIN)
                .path("/")
                .httpOnly(true)
                .secure(COOKIE_SECURE)
                .sameSite(COOKIE_SAME_SITE)
                .build()

            val refreshToken: String = jwtUtil.generateRefreshToken(memberId)
    //        val encodeToken: String = Base64.getUrlEncoder().encodeToString(refreshToken.toByteArray())

            val refreshCookie: ResponseCookie = ResponseCookie.from("refreshToken", refreshToken)
                .domain(COOKIE_DOMAIN)
                .path("/")
                .httpOnly(true)
                .secure(COOKIE_SECURE)
                .sameSite(COOKIE_SAME_SITE)
                .build()

            response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString())
            response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString())

            return ResponseEntity.ok(LoginResponse(accessToken = accessToken, memberId = memberId.toLong(), memberName = memberName, isLogin = true))
        } catch (e: BadCredentialsException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    @GetMapping("/login")
    fun login(
        @CookieValue("accessToken", required = false) accessToken: String?,
    ): ResponseEntity<LoginResponse> {

        // if (accessToken == null || accessToken == "") throw NullPointerException("accessToken is missing")

        val jwtResult = jwtUtil.validateToken(accessToken)

        if(jwtResult is JwtResult.Failure) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(LoginResponse(failCode = jwtResult.errorType.name))
        }

        val claim: Claims = jwtUtil.getClaimsFromToken(accessToken)
        val memberId: Long = claim.subject.toLong()

        val findMember: MemberDto = memberService.findById(memberId)
        log.info("::::::::::::: findMember")
        val memberName: String = findMember.name!!

        return ResponseEntity
            .ok(LoginResponse(
                accessToken = accessToken,
                memberId = memberId,
                memberName = memberName,
                isLogin = true))
    }

    @PostMapping("/users")
    fun signUp(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<Member> {

        val newMember = Member(
            loginId = signUpRequest.loginId,
            loginPw = passwordEncoder.encode(signUpRequest.loginPw),
            name = signUpRequest.name,
            mobileNumber = signUpRequest.mobileNumber
        )

        memberService.register(newMember, Roles.USER.name)

        return ResponseEntity.ok(newMember)

    }

    @GetMapping("/users/{id}")
    fun getUserInfo(@PathVariable("id") id: Long): ResponseEntity<MemberDto> {

        val dto: MemberDto = memberService.findById(id)
        return ResponseEntity.ok(dto)
    }

    @PostMapping("/accessToken")
    fun refreshAccessToken(
        @CookieValue refreshToken: String?,
        response: HttpServletResponse
    ): ResponseEntity<LoginResponse?> {

        var status = HttpStatus.OK
        var message = ""


        val jwtResult = jwtUtil.validateToken(refreshToken)
        if(jwtResult is JwtResult.Failure) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(LoginResponse(
                    failCode = jwtResult.errorType.name,
                    tokenType = TokenType.REFRESH.name))
        }

        val claim: Claims = jwtUtil.getClaimsFromToken(refreshToken)
        val memberId: Long = claim.subject.toLong()

        val findMember: MemberDto = memberService.findById(memberId)
        val memberName: String = findMember.name!!
        val roles: MutableList<String>? = findMember.roles

//            val convertedRoles: List<String>?
//            if(roles != null && roles.size > 0) {
//                convertedRoles = roles.map { it -> it.authority }
//            } else {
//                convertedRoles = null
//            }

        val accessToken: String = jwtUtil.generateAccessToken(memberId.toString(), roles)
        log.info("accessToken = {}", accessToken)
        val accessCookie: ResponseCookie = ResponseCookie.from("accessToken", accessToken)
            .domain(COOKIE_DOMAIN)
            .path("/")
            .httpOnly(true)
            .secure(COOKIE_SECURE)
            .sameSite(COOKIE_SAME_SITE)
            .build()
        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString())

        return ResponseEntity
            .ok(LoginResponse(
                accessToken = accessToken,
                memberId = memberId,
                memberName = memberName,
                isLogin = true))

    }

    @GetMapping("/duplicateCheck")
    fun duplicateCheck(@RequestParam loginId: String): ResponseEntity<String> {
        val checkResult = memberService.duplicateCheck(loginId)
        return ResponseEntity.ok(checkResult)
    }

    @GetMapping("/roles")
    fun findRoles(): ResponseEntity<List<String>> {

        val roles: List<String> = authorityService.findRoles()
        return ResponseEntity.ok(roles)

    }

    data class SignUpRequest(
        val loginId: String,
        val loginPw: String,
        val name: String,
        val mobileNumber: String
    )

    data class LoginRequest(
        val loginId: String,
        val loginPw: String
    )

    data class LoginResponse(
        val tokenType: String = TokenType.ACCESS.name,
        val failCode: String? = null,
        val accessToken: String? = null,
        val memberId: Long? = null,
        val memberName: String? = null,
        val isLogin: Boolean? = null
    )

}