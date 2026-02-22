package com.shopping.admin.domain.system.entity

import com.common.jpa.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "screen")
class Screen (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screen_id")
    var id: Long? = null,

    @Column(name = "screen_name")
    var name: String,

    var path: String,

    var useYn: String? = null,
) : BaseEntity()