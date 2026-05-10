package com.shopping.store.dto

data class CategoryRegisterRequest(
    val name: String,
    val parentId: Long?,
    val sortOrder: Int,
    val depth: Int,
    val isUse: Boolean,
)

data class CategoryUpdateRequest(
    val name: String,
    val parentId: Long?,
    val sortOrder: Int,
    val isUse: Boolean,
)

data class CategoryResponse(
    val id: Long?,
    val name: String,
    val parentId: Long?,
    val sortOrder: Int,
    val depth: Int,
    val isUse: Boolean,
)
