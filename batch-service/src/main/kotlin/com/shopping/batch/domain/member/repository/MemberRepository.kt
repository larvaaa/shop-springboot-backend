package com.shopping.batch.domain.member.repository

import com.shopping.batch.domain.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long>, MemberRepositoryCustom {

    fun findByLoginId(username: String): Member?

}