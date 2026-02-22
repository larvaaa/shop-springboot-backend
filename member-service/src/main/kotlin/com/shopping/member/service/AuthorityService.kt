package com.shopping.member.service

import com.shopping.member.repository.RoleRepository
import org.springframework.stereotype.Service

@Service
class AuthorityService(
    private val roleRepository: RoleRepository
) {

    fun findRoles(): List<String> = roleRepository.findRoles()

}