package com.shopping.store.dto

data class BrandDto(
    val id: Long,
    var name: String,
    var attachFileId: Long? = null,
    var isUse: Boolean = true
)
