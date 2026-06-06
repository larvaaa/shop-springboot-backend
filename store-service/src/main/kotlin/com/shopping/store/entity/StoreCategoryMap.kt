package com.shopping.store.entity

import com.common.jpa.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "store_category_map")
class StoreCategoryMap(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_category_id")
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    val store: Store,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    val category: Category,

    @Column(name = "is_deleted")
    val isDeleted: Boolean = false,

) : BaseEntity()