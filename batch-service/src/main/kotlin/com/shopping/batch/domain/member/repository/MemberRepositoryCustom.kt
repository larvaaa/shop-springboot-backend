package com.shopping.batch.domain.member.repository

import com.shopping.batch.domain.member.dto.MemberDto

interface MemberRepositoryCustom {

    fun findMemberAndAuthority(username: String): MemberDto

}