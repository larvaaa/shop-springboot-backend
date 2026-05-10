package com.shopping.store.dto

import com.shopping.store.entity.DayOfWeek
import java.time.LocalTime

data class StoreOperationHourDto(
    val id: Long,
    val store_id: Long,
    val dayOfWeek: DayOfWeek,
    val openTime: LocalTime,
    val closeTime: LocalTime,
    val breakStart: LocalTime,
    val breakEnd: LocalTime,
    val isDayOff: Boolean,
)
