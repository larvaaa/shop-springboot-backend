package com.shopping.batch.domain.member.repository

import com.shopping.batch.domain.member.entity.MemberRole
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRoleRepository : JpaRepository<MemberRole, Long> {

    fun save(memberRole: MemberRole): MemberRole
}