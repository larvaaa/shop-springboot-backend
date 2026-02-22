package com.shopping.member.repository

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.shopping.member.dto.MemberDto
import com.shopping.member.dto.SearchDto
import com.shopping.member.entity.Member
import com.shopping.member.entity.QMember.member
import com.shopping.member.entity.QMemberRole.memberRole
import com.shopping.member.entity.QRole.role
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long>, MemberRepositoryCustom {

    fun findByLoginId(loginId: String): Member?

}

interface MemberRepositoryCustom {

    fun findMemberAndAuthority(username: String): MemberDto

    fun findMembers(dto: SearchDto): List<MemberDto>
}

class MemberRepositoryCustomImpl(
    private val jPAQueryFactory: JPAQueryFactory
) : MemberRepositoryCustom {

    override fun findMemberAndAuthority(username: String): MemberDto {

        return jPAQueryFactory
            .select(
                Projections.constructor(
                    MemberDto::class.java,
                    member.loginId,
                    member.loginPw,
                    member.name,
                    member.mobileNumber,
                    member.id,
                    Projections.list(role)
                )
            )
            .from(member)
            .leftJoin(member.roles, memberRole)
            .leftJoin(memberRole.role, role)
            .where(member.loginId.eq(username))
            .fetchOne()!!
    }

    override fun findMembers(dto: SearchDto): List<MemberDto> {

        return jPAQueryFactory
            .select(
                Projections.constructor(
                    MemberDto::class.java,
                    member.loginId,
                    member.loginPw,
                    member.name,
                    member.mobileNumber,
                    member.id,
                    Projections.list(role.roleName)
                )
            )
            .from(member)
            .leftJoin(member.roles, memberRole)
            .leftJoin(memberRole.role, role)
            .where(
                loginIdLike(dto.loginId),
                nameLike(dto.name),
                mobileNumberLike(dto.mobileNumber),
            )
            .fetch()
    }

    private fun loginIdLike(loginId: String?): BooleanExpression? {
        return if (loginId.isNullOrBlank()) null else member.loginId.like("%${loginId}%")
    }

    private fun nameLike(name: String?): BooleanExpression? {
        return if (name.isNullOrBlank()) null else member.name.like("%${name}%")
    }

    private fun mobileNumberLike(number: String?): BooleanExpression? {
        return if (number.isNullOrBlank()) null else member.mobileNumber.like("%${number}%")
    }


}