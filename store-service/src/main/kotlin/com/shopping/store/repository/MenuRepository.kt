package com.shopping.store.repository

import com.shopping.store.entity.Menu
import org.springframework.data.jpa.repository.JpaRepository

interface MenuRepository : JpaRepository<Menu, Long> {

    fun findMenuById(id: Long): Menu

    fun findAllByStoreIdOrderBySortOrder(storeId: Long): List<Menu>
}
