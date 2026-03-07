package com.shopping.batch.domain.member.entity

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority

@Entity
@Table(name = "role")
class Role (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    var id: Long? = null,

    @Column(name = "role_name")
    @get:JvmName("getAuthorityProperty")  // getter의 JVM 이름을 getAuthorityProperty로 변경
    val authority: String

) : GrantedAuthority {

    override fun getAuthority(): String = authority

}