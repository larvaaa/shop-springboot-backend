package com.shopping.member.dto

data class MemberDto(
    val loginId: String?,

    var loginPw: String?,

    var name: String?,

    var mobileNumber: String?,

    val id: Long,

    val roles: MutableList<String>? = null
)

