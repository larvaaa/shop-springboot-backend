package com.shopping.store.repository

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import com.shopping.store.dto.SearchType
import com.shopping.store.dto.StoreFindRequest
import com.shopping.store.dto.StoreFindResponse
import com.shopping.store.entity.BusinessStatus
import com.shopping.store.entity.QBrand.brand
import com.shopping.store.entity.QStore.store
import com.shopping.store.entity.QStoreCategoryMap.storeCategoryMap
import com.shopping.store.entity.Store
import org.springframework.data.jpa.repository.JpaRepository

interface StoreRepository : JpaRepository<Store, Long>, StoreRepositoryCustom {

    fun findStoreById(id: Long): Store

    fun findAllByIsUse(isUse: Boolean = true): List<Store>

    override fun delete(store: Store)
}

interface StoreRepositoryCustom {
    fun findStores(dto: StoreFindRequest): List<StoreFindResponse>
}

class StoreRepositoryCustomImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : StoreRepositoryCustom {

    override fun findStores(dto: StoreFindRequest): List<StoreFindResponse> {
        return jpaQueryFactory
            .select(Projections.constructor(
                StoreFindResponse::class.java,
                store.id,
                store.name,
                brand.id,
                brand.name,
                store.address,
                store.phone,
                store.minOrderPrice,
                store.estimatedDeliveryTime,
                store.businessStatus,
                store.isUse,
                Expressions.nullExpression(String::class.java),
            ))
            .from(store)
            .leftJoin(store.brand, brand)
            .where(
                searchCondition(dto.searchType, dto.searchKeyword),
                categoryIdsIn(dto.categoryIds),
                businessStatusEq(dto.businessStatus),
                isUseEq(dto.isUse),
            )
            .orderBy(store.createdBy.desc())
            .fetch()
    }

    private fun searchCondition(searchType: SearchType?, keyword: String?): BooleanExpression? {
        if (searchType == null || keyword.isNullOrBlank()) return null
        return when (searchType) {
            SearchType.STORE_NAME -> store.name.like("%$keyword%")
            SearchType.PHONE      -> store.phone.like("%$keyword%")
            SearchType.BRAND_NAME -> brand.name.like("%$keyword%")
        }
    }

    private fun categoryIdsIn(categoryIds: List<Long>?): BooleanExpression? {
        if (categoryIds.isNullOrEmpty()) return null
        return JPAExpressions.selectOne()
            .from(storeCategoryMap)
            .where(
                storeCategoryMap.store.eq(store),
                storeCategoryMap.category.id.`in`(categoryIds),
            )
            .exists()
    }

    private fun businessStatusEq(status: BusinessStatus?): BooleanExpression? =
        status?.let { store.businessStatus.eq(it) }

    private fun isUseEq(isUse: Boolean?): BooleanExpression? =
        isUse?.let { store.isUse.eq(it) }
}
