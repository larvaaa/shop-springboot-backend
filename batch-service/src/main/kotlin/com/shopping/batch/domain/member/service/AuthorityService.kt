package com.shopping.batch.domain.member.service

import com.shopping.batch.domain.member.repository.RoleRepository
import org.springframework.stereotype.Service

@Service
class AuthorityService(
    private val roleRepository: RoleRepository
) {

    fun findRoles(): List<String> = roleRepository.findRoles()

}