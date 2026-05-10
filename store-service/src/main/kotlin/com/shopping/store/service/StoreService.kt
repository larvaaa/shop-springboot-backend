package com.shopping.store.service

import com.shopping.store.dto.StoreRegisterRequest
import com.shopping.store.entity.Store
import com.shopping.store.entity.StoreOperationHour
import com.shopping.store.repository.StoreOperationHourRepository
import com.shopping.store.repository.StoreRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class StoreService(
    private val storeRepository: StoreRepository,
    private val storeOperationHourRepository: StoreOperationHourRepository
) {

    @Transactional(readOnly = true)
    fun getStore(id: Long) {
        TODO()
    }

    @Transactional(readOnly = true)
    fun getStores() {
        TODO()
    }

    fun registerStore(dto: StoreRegisterRequest) {

        val store = Store(
            name = dto.name,
            postalCode = dto.postalCode,
            address = dto.address,
            detailAddress = dto.detailAddress,
            phone = dto.phone,
            minOrderPrice = dto.minOrderPrice,
            estimatedDeliveryTime = dto.estimatedDeliveryTime,
        )

        storeRepository.save(store)

        dto.storeOperationHour.forEach {
            h -> storeOperationHourRepository.save(
                StoreOperationHour(
                    store = store,
                    dayOfWeek = h.dayOfWeek,
                    openTime = h.openTime,
                    closeTime = h.closeTime,
                    breakStart = h.breakStart,
                    breakEnd = h.breakEnd,
                    isDayOff = h.isDayOff,
                )
            )
        }

    }

    fun updateStore(id: Long) {
        TODO()
    }
}
