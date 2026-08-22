package com.shopping.store.repository

import com.shopping.store.entity.OptionDetail
import com.shopping.store.entity.OptionGroup
import org.springframework.data.jpa.repository.JpaRepository

interface OptionDetailRepository : JpaRepository<OptionDetail, Long> {

    fun findAllByOptionGroupOrderBySortOrder(optionGroup: OptionGroup): List<OptionDetail>

    fun deleteByOptionGroup(optionGroup: OptionGroup)
}
