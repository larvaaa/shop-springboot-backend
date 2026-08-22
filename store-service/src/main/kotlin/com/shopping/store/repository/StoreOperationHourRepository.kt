package com.shopping.store.repository

import com.shopping.store.entity.Store
import com.shopping.store.entity.StoreOperationHour
import org.springframework.data.jpa.repository.JpaRepository

interface StoreOperationHourRepository : JpaRepository<StoreOperationHour, Long>, StoreOperationHourRepositoryCustom {

    fun deleteByStore(store: Store)
}

interface StoreOperationHourRepositoryCustom {


}

class StoreOperationHourRepositoryCustomImpl : StoreOperationHourRepositoryCustom {

}

