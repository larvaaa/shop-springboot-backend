package com.example.batch.repository

import com.example.batch.domain.member.repository.MemberRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class MemberRepositoryImplTest {

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Test
    fun test() {
//        val memberDto: MemberDto = memberRepositoryImpl.findMemberAndAuthority("user01")
//        println(memberDto)
//        val member = memberRepository.findByLoginId("user01")
//        for (role in member.roles!!) {
//            println(role.authority.role)
//        }
        println("result = ${test2()}")
    }

    fun test2(): Boolean? {

        val member = memberRepository.findByLoginId("user03")

        return if (member != null) {
            true
        } else {
            false
        }
    }
}