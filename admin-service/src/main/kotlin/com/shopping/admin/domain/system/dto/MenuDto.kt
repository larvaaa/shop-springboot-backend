package com.shopping.admin.domain.system.dto

import java.time.LocalDateTime

class MenuDto (

    val menuId: Long?,

    val menuName: String,

    val parentMenuName: String,

    val level: Int,

    val sort: Int,

    val useYn: Char,

    val parentId: Long?,

    val screenId: Long?,

    val screenName: String?,

    val screenPath: String?,

    val createdBy: String?,

    val createdDate: LocalDateTime?,

    val lastModifiedBy: String?,

    val lastModifiedDate: LocalDateTime?,
)

