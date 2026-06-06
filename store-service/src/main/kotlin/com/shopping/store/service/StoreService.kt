package com.shopping.store.service

import com.shopping.store.dto.StoreRegisterRequest
import com.shopping.store.entity.Store
import com.shopping.store.entity.StoreCategoryMap
import com.shopping.store.entity.StoreOperationHour
import com.shopping.store.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class StoreService(
    private val storeRepository: StoreRepository,
    private val storeOperationHourRepository: StoreOperationHourRepository,
    private val brandRepository: BrandRepository,
    private val categoryRepository: CategoryRepository,
    private val storeCategoryMapRepository: StoreCategoryMapRepository,
) {

    @Transactional(readOnly = true)
    fun getStore(id: Long) {
        TODO()
    }

    @Transactional(readOnly = true)
    fun getStores() {
        TODO()
    }

    @Transactional
    fun registerStore(dto: StoreRegisterRequest) {

        // let(스코프 함수)
        // let은 "내 앞의 객체를 괄호 { } 안으로 쏙 던져줄 테니까, 그걸로 지지고 볶아서 결과를 내놔!"라는 함수입니다. 이때 던져진 객체는 이름이 없기 때문에 코틀린에서는 기본적으로 it이라는 대명사로 부릅니다.
        // dto에 brandId가 있는데 조회 결과가 없을경우 진행을 중지하고 프론트엔드로 브랜드가 없음을 알린다
        val brand = dto.brandId?.let {
            brandRepository.findEntityById(it) ?: throw IllegalStateException("존재하지 않는 brandId => ${dto.brandId}")
        }

        val store = Store(
            name = dto.name,
            brand = brand,
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

        val categoryIds = dto.categoryIds
        categoryIds.forEach {
            val category = categoryRepository.findEntityById(it) ?: throw IllegalStateException("존재하지 않는 categoryId => ${it}")
            storeCategoryMapRepository.save(
                StoreCategoryMap(
                    store = store,
                    category = category,
                )
            )
        }

    }

    fun updateStore(id: Long) {
        TODO()
    }
}
