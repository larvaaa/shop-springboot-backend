package com.shopping.store.entity

import com.common.jpa.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "brand")
class Brand (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    var id: Long? = null,

    @Column(name = "brand_name")
    var name: String,

    @Column(name = "attach_file_id")
    var attachFileId: Long? = null,

    @Column(name = "is_use")
    var isUse: Boolean = true

) : BaseEntity()