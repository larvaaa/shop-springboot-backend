package com.shopping.admin.domain.system.dto

class MenuDto (

    val menuId: Long?,

    val menuName: String,

    val level: Int,

    val sort: Int,

    val useYn: Char,

    val parentId: Long?,

    val screenId: Long?,

    val screenName: String?,

    val screenPath: String?,
)

