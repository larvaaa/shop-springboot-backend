package com.shopping.store.repository

import com.shopping.store.entity.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long>, CategoryRepositoryCustom {

    fun findAllByOrderBySortOrder(): List<Category>

    fun findEntityById(id: Long): Category?
}

interface CategoryRepositoryCustom {

}

class CategoryRepositoryCustomImpl : CategoryRepositoryCustom {

}
