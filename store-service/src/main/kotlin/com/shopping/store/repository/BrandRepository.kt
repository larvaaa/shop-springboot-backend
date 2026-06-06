package com.shopping.store.repository

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.shopping.store.dto.BrandFindRequest
import com.shopping.store.dto.BrandFindResponse
import com.shopping.store.entity.Brand
import com.shopping.store.entity.QBrand.brand
import org.springframework.data.jpa.repository.JpaRepository

interface BrandRepository : JpaRepository<Brand, Long>, BrandRepositoryCustom {

    fun findEntityById(id: Long): Brand?
    fun findBrandById(id: Long): BrandFindResponse
}

interface BrandRepositoryCustom {

    fun findBrands(dto: BrandFindRequest): List<BrandFindResponse>
}

class BrandRepositoryCustomImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : BrandRepositoryCustom {

    override fun findBrands(dto: BrandFindRequest): List<BrandFindResponse> {
        return jpaQueryFactory
            .select(
                Projections.constructor(
                    BrandFindResponse::class.java,
                    brand.id,
                    brand.name,
                    brand.description,
                    brand.attachFileId,
                    brand.isUse
                )
            )
            .from(brand)
            .where(
                nameLike(dto.name),
                isUseEq(dto.isUse)
            )
            .fetch()
    }

    private fun nameLike(name: String?): BooleanExpression? {
        return if (name.isNullOrBlank()) null else brand.name.like("%$name%")
    }

    private fun isUseEq(isUse: Boolean?): BooleanExpression? {
        return if (isUse == null) null else brand.isUse.eq(isUse)
    }

}
