package com.shopping.member.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.shopping.member.entity.QRole.role
import com.shopping.member.entity.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long>, RoleRepositoryCustom {

    fun findByRoleName(role: String): Role

}

interface RoleRepositoryCustom {

    fun findRoles(): List<String>

}

class RoleRepositoryCustomImpl(
    private val jPAQueryFactory: JPAQueryFactory
) : RoleRepositoryCustom {

    override fun findRoles(): List<String> {
        return jPAQueryFactory
            .select(role.roleName)
            .from(role)
            .fetch()
    }
}