package com.shopping.member.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.shopping.member.entity.MemberRole
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRoleRepository : JpaRepository<MemberRole, Long>, MemberRoleRepositoryCustom {

    fun save(memberRole: MemberRole): MemberRole
}

interface MemberRoleRepositoryCustom {


}

class MemberRoleRepositoryCustomImpl(
    private val jPAQueryFactory: JPAQueryFactory
) : MemberRoleRepositoryCustom {

}