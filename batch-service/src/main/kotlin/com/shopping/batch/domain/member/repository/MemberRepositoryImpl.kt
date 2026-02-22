package com.shopping.batch.domain.member.repository

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import com.shopping.batch.domain.member.dto.MemberDto
import com.shopping.batch.domain.member.entity.QMember.member
import com.shopping.batch.domain.member.entity.QMemberRole.memberRole
import com.shopping.batch.domain.member.entity.QRole.role

class MemberRepositoryImpl(
    private val jPAQueryFactory: JPAQueryFactory
) : MemberRepositoryCustom {

    override fun findMemberAndAuthority(username: String): MemberDto {

        return jPAQueryFactory
            .select(Projections.constructor(
                MemberDto::class.java,
                member.loginId,
                member.loginPw,
                member.name,
                member.mobileNumber,
                member.id,
                Projections.list(role)
            ))
            .from(member)
            .leftJoin(member.roles, memberRole)
            .leftJoin(memberRole.role, role)
            .where(member.loginId.eq(username))
            .fetchOne()!!
    }

}