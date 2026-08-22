package com.shopping.store.repository

import com.shopping.store.entity.Store
import com.shopping.store.entity.StoreCategoryMap
import org.springframework.data.jpa.repository.JpaRepository

interface StoreCategoryMapRepository : JpaRepository<StoreCategoryMap, Long>, StoreCategoryMapCustom {

    fun findByStore(store: Store): List<StoreCategoryMap>

    fun findByStoreIdIn(storeIds: List<Long>): List<StoreCategoryMap>

    fun deleteByStore(store: Store)
}

interface StoreCategoryMapCustom {


}

class StoreCategoryMapCustomImpl : StoreCategoryMapCustom {

}
