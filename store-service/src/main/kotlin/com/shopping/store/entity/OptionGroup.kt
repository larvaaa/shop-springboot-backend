package com.shopping.store.entity

import com.common.jpa.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "option_group")
class OptionGroup(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_group_id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    var menu: Menu,

    @Column(name = "option_group_name")
    var name: String,

    @Column(name = "is_required")
    var isRequired: Boolean = false,

    @Column(name = "is_multi_selected_enabled")
    var isMultiSelectedEnabled: Boolean = false,

    @Column(name = "sort_order")
    var sortOrder: Int = 0,

) : BaseEntity()
