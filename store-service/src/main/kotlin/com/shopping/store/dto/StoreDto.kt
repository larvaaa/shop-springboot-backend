package com.shopping.store.dto

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


