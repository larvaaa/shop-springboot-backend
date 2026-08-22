package com.shopping.store.entity

import com.common.jpa.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "menu")
class Menu(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    var store: Store,

    @Column(name = "menu_name")
    var name: String,

    @Column(name = "description")
    var description: String? = null,

    @Column(name = "price")
    var price: Int,

    @Column(name = "attach_file_id")
    var attachFileId: Long? = null,

    @Column(name = "is_use")
    var isUse: Boolean = true,

    @Column(name = "is_sold_out")
    var isSoldOut: Boolean = false,

    @Column(name = "sort_order")
    var sortOrder: Int = 0,

) : BaseEntity()
