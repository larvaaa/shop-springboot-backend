package com.common.security.entity

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority

@Entity
@Table(name = "role")
class Role (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    var id: Long? = null,

    val roleName: String

) : GrantedAuthority {

    override fun getAuthority(): String = roleName

}