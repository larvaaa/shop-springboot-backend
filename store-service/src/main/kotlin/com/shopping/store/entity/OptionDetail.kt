package com.shopping.store.entity

import com.common.jpa.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "option_detail")
class OptionDetail(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_detail_id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id", nullable = false)
    var optionGroup: OptionGroup,

    // DB 컬럼명 오타(option_deatil_name)가 기존 스키마에 이미 존재 — 그대로 매핑
    @Column(name = "option_deatil_name")
    var name: String,

    @Column(name = "extra_price")
    var extraPrice: Int = 0,

    @Column(name = "is_use")
    var isUse: Boolean = true,

    @Column(name = "sort_order")
    var sortOrder: Int = 0,

) : BaseEntity()
