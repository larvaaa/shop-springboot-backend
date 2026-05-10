package com.shopping.store.repository

import com.shopping.store.entity.Store
import org.springframework.data.jpa.repository.JpaRepository

interface StoreRepository : JpaRepository<Store, Long>, StoreRepositoryCustom {

    fun findStoreById(id: Long): Store

    fun findAllByIsUse(isUse: Boolean = true): List<Store>

    override fun delete(store: Store)
}

interface StoreRepositoryCustom {

}

class StoreRepositoryCustomImpl : StoreRepositoryCustom {

}
