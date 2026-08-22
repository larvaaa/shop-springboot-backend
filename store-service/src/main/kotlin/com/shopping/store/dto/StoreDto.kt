package com.shopping.store.dto

import com.shopping.store.entity.BusinessStatus

enum class SearchType {
    STORE_NAME, PHONE, BRAND_NAME
}

data class StoreRegisterRequest(
    val name: String,
    val brandId: Long?,
    val categoryIds: List<Long>,
    val postalCode: String,
    val address: String,
    val detailAddress: String,
    val phone: String,
    val minOrderPrice: Int,
    val estimatedDeliveryTime: Int,
    val storeOperationHour: List<StoreOperationHourDto>,
)

/*
get 방식에서 리스트 형식 송수신 방법
둘 다 List<Long>으로 정상 수신됩니다.

# 방식 1 - 콤마 구분 (현재 방식)
?categoryIds=11,2,12,13

# 방식 2 - 반복 파라미터
?categoryIds=11&categoryIds=2&categoryIds=12&categoryIds=13
*/
data class StoreFindRequest(
    val searchType: SearchType? = null,
    val searchKeyword: String? = null,
    val categoryIds: List<Long>? = null,
    val businessStatus: BusinessStatus? = null,
    val isUse: Boolean? = null,
)

data class StoreFindResponse(
    val id: Long?,
    val name: String,
    val brandId: Long?,
    val brandName: String?,
    val address: String,
    val phone: String,
    val minOrderPrice: Int,
    val estimatedDeliveryTime: Int,
    val businessStatus: BusinessStatus,
    val isUse: Boolean,
    val categoryNames: String?,
)

data class StoreDetailResponse(
    val id: Long?,
    val name: String,
    val brandId: Long?,
    val brandName: String?,
    val postalCode: String,
    val address: String,
    val detailAddress: String,
    val phone: String,
    val minOrderPrice: Int,
    val estimatedDeliveryTime: Int,
    val businessStatus: BusinessStatus,
    val description: String?,
    val isUse: Boolean,
    val operationHours: List<StoreOperationHourDto>,
    val categoryIds: List<Long>,
)
