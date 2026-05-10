package com.shopping.admin.domain.system.entity

import com.common.jpa.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "menu")
class Menu (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    var id: Long? = null,

    @Column(name = "menu_name")
    var name: String,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id")
    var screen: Screen? = null,

    val level: Int,

    var sort: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    val parent: Menu? = null,

    var useYn: Char = 'Y',

) : BaseEntity()

