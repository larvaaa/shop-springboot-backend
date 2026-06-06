package com.shopping.store.dto

import org.springframework.web.multipart.MultipartFile

data class BrandRegisterRequest(
    val name: String,
    val description: String? = null,
    val attachFileId: Long? = null,
    val isUse: Boolean = true,
    val file: MultipartFile? = null,
)

data class BrandFindRequest(
    val name: String?,
    val isUse: Boolean?,
    val page: Int,
    val size: Int,
)

data class BrandFindResponse(
    val id: Long,
    val name: String,
    val description: String? = null,
    val attachFileId: Long? = null,
    val isUse: Boolean = true,
)
