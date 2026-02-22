package com.shopping.batch.domain.member.repository

import com.shopping.batch.domain.member.entity.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long>, RoleRepositoryCustom {

    fun findByRoleName(role: String): Role

}