package com.shopping.store.repository

import com.shopping.store.entity.StoreCategoryMap
import org.springframework.data.jpa.repository.JpaRepository

interface StoreCategoryMapRepository : JpaRepository<StoreCategoryMap, Long>, StoreCategoryMapCustom {


}

interface StoreCategoryMapCustom {


}

class StoreCategoryMapCustomImpl : StoreCategoryMapCustom {

}
