package com.shopping.member.security

import com.shopping.member.dto.MemberDto
import com.shopping.member.entity.Member
import com.shopping.member.entity.Role
import com.shopping.member.repository.MemberRepository
import org.slf4j.LoggerFactory
import org.springframework.security.core.CredentialsContainer
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailService(
    val memberRepository: MemberRepository
) : UserDetailsService {

    private val log = LoggerFactory.getLogger(this.javaClass)!!

    override fun loadUserByUsername(loginId: String): UserDetails? {

        val findMember: Member = memberRepository.findByLoginId(loginId) ?: return null

        val customUserDetails = CustomUserDetails(
            loginId = findMember.loginId!!,
            loginPw = findMember.loginPw!!,
            id = findMember.id!!,
            roles = findMember.roles?.map { it.role },
            name = findMember.name!!
        )

        return customUserDetails
    }

    class CustomUserDetails(
        val loginId: String,
        var loginPw: String?,
        var name: String,
        val id: Long,
        val roles: List<GrantedAuthority>?
    ) : UserDetails,
        CredentialsContainer {

        override fun getAuthorities(): List<GrantedAuthority>? = this.roles

        override fun getUsername(): String = this.loginId

        override fun getPassword(): String? = this.loginPw

        override fun eraseCredentials() {
            this.loginPw = null
        }
    }

}