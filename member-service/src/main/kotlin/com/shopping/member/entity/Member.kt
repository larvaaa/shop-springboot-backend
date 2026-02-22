package com.shopping.member.entity

import com.common.jpa.entity.BaseEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "member")
class Member(

    var loginId: String?,

    var loginPw: String?,

    var name: String? = null,

    var mobileNumber: String? = null,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    var id: Long? = null,

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    var roles: MutableList<com.shopping.member.entity.MemberRole>? = null

) : BaseEntity()