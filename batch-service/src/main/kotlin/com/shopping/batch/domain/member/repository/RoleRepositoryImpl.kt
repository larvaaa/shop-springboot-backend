package com.shopping.batch.domain.member.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.shopping.batch.domain.member.entity.QRole.role

class RoleRepositoryImpl(
    private val jPAQueryFactory: JPAQueryFactory
) : RoleRepositoryCustom {

    override fun findRoles(): List<String> {
        return jPAQueryFactory
            .select(role.authority)
            .from(role)
            .fetch()
    }
}