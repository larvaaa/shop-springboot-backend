package com.shopping.member.service

import com.shopping.member.dto.MemberDto
import com.shopping.member.dto.SearchDto
import com.shopping.member.entity.Member
import com.shopping.member.entity.MemberRole
import com.shopping.member.repository.MemberRepository
import com.shopping.member.repository.MemberRoleRepository
import com.shopping.member.repository.RoleRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class MemberService(
    val memberRepository: MemberRepository,
    val memberRoleRepository: MemberRoleRepository,
    val roleRepository: RoleRepository
) {

    private val log = LoggerFactory.getLogger(this.javaClass)!!

    fun findByLoginId(loginId: String): Member? = memberRepository.findByLoginId(loginId)

    fun register(member: Member, role: String): Member {

        memberRepository.save(member)

        val findRole = roleRepository.findByRoleName(role)
        val memberRole = MemberRole(member = member, role = findRole)
        memberRoleRepository.save(memberRole)

        return memberRepository.save(member)
    }

    fun findById(id: Long): MemberDto {
        val findMember: Member = memberRepository.findById(id).orElseThrow()

        val roles: MutableList<String>? = findMember.roles?.map { it.role.roleName } as MutableList?
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

    fun findMembers(dto: SearchDto): List<MemberDto> = memberRepository.findMembers(dto)

}