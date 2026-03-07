package com.example.batch.service

import com.shopping.batch.domain.member.dto.MemberDto
import com.shopping.batch.domain.member.service.MemberService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    lateinit var memberService: MemberService

    @Test
    fun findById() {

        val findMember: MemberDto = memberService.findById(2L);

    }
}