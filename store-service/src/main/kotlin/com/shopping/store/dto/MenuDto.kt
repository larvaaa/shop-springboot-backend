package com.shopping.store.dto

data class OptionDetailDto(
    val id: Long? = null,
    val name: String,
    val extraPrice: Int = 0,
    val isUse: Boolean = true,
    val sortOrder: Int = 0,
)

data class OptionGroupDto(
    val id: Long? = null,
    val name: String,
    val isRequired: Boolean = false,
    val isMultiSelectedEnabled: Boolean = false,
    val sortOrder: Int = 0,
    val options: List<OptionDetailDto> = emptyList(),
)

data class MenuRegisterRequest(
    val storeId: Long,
    val name: String,
    val description: String? = null,
    val price: Int,
    val attachFileId: Long? = null,
    val isUse: Boolean = true,
    val isSoldOut: Boolean = false,
    val sortOrder: Int = 0,
    val optionGroups: List<OptionGroupDto> = emptyList(),
)

data class MenuFindRequest(
    val storeId: Long,
    val searchKeyword: String? = null,
)

data class MenuFindResponse(
    val id: Long?,
    val name: String,
    val price: Int,
    val attachFileId: Long?,
    val isUse: Boolean,
    val isSoldOut: Boolean,
    val sortOrder: Int,
)

data class MenuDetailResponse(
    val id: Long?,
    val storeId: Long,
    val name: String,
    val description: String?,
    val price: Int,
    val attachFileId: Long?,
    val isUse: Boolean,
    val isSoldOut: Boolean,
    val sortOrder: Int,
    val optionGroups: List<OptionGroupDto>,
)
