package com.shopping.store.entity

import com.common.jpa.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "category")
class Category (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    var id: Long? = null,

    @Column(name = "category_name")
    var name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parentId: Category? = null,

    @Column(name = "sort_order")
    var sortOrder: Int,

    @Column(name = "depth")
    val depth: Int,

    @Column(name = "is_use")
    var isUse: Boolean,

) : BaseEntity()