package com.shopping.member.controller

import com.common.core.api.ApiResponse
import com.shopping.member.dto.MemberDto
import com.shopping.member.dto.SearchDto
import com.shopping.member.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController(
    private val memberService: MemberService
) {

    @GetMapping("/member")
    fun getMemberList(dto: SearchDto): ResponseEntity<ApiResponse<List<MemberDto>>> {
        return ResponseEntity.ok(ApiResponse.success(memberService.findMembers(dto)))
    }
}