package com.shopping.batch.domain.member.dto

import org.springframework.security.core.GrantedAuthority

data class MemberDto(
    val loginId: String?,

    var loginPw: String?,

    var name: String?,

    var mobileNumber: String?,

    val id: Long,

    val roles: MutableList<GrantedAuthority>? = null
)

