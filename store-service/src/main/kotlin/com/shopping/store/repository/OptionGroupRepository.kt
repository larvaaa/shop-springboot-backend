package com.shopping.store.repository

import com.shopping.store.entity.Menu
import com.shopping.store.entity.OptionGroup
import org.springframework.data.jpa.repository.JpaRepository

interface OptionGroupRepository : JpaRepository<OptionGroup, Long> {

    fun findAllByMenuOrderBySortOrder(menu: Menu): List<OptionGroup>
}
