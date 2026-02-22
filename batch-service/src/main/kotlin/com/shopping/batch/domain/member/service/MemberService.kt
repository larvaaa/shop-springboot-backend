package com.shopping.batch.domain.member.service

import com.shopping.batch.domain.member.dto.MemberDto
import com.shopping.batch.domain.member.entity.Member
import com.shopping.batch.domain.member.entity.MemberRole
import com.shopping.batch.domain.member.repository.MemberRepository
import com.shopping.batch.domain.member.repository.MemberRoleRepository
import com.shopping.batch.domain.member.repository.RoleRepository
import org.slf4j.LoggerFactory
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Service

@Service
class MemberService(
    val memberRepository: MemberRepository,
    val memberRoleRepository: MemberRoleRepository,
    val roleRepository: RoleRepository
) {

    private val log = LoggerFactory.getLogger(this.javaClass)!!

    fun findByLoginId(username: String): Member? = memberRepository.findByLoginId(username)

    fun register(member: Member, role: String): Member {

        memberRepository.save(member)

        val role = roleRepository.findByRoleName(role)
        val memberRole = MemberRole(member = member, role = role)
        memberRoleRepository.save(memberRole)

        return memberRepository.save(member)
    }

    fun findById(id: Long): MemberDto {
        val findMember: Member = memberRepository.findById(id).orElseThrow()

        val roles: MutableList<GrantedAuthority>? = findMember.roles as MutableList<GrantedAuthority>?
        val memberDto: MemberDto = MemberDto(
            name = findMember.name!!,
            mobileNumber = findMember.mobileNumber!!,
            loginId = null,
            loginPw = null,
            id = findMember.id!!,
            roles = roles
        )

        return memberDto
    }

    fun duplicateCheck(loginId: String): String {
        return if(findByLoginId(loginId) == null) "Y" else "N"
    }

}