package com.shopping.member.entity

import jakarta.persistence.*

@Entity
@Table(name = "member_role")
class MemberRole(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_role_id")
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: com.shopping.member.entity.Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    val role: com.shopping.member.entity.Role

)