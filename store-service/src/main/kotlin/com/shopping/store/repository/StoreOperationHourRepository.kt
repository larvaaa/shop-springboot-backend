package com.shopping.store.repository

import com.shopping.store.entity.StoreOperationHour
import org.springframework.data.jpa.repository.JpaRepository

interface StoreOperationHourRepository : JpaRepository<StoreOperationHour, Long>, StoreOperationHourRepositoryCustom {
}

interface StoreOperationHourRepositoryCustom {


}

class StoreOperationHourRepositoryCustomImpl : StoreOperationHourRepositoryCustom {

}

