package com.shopping.admin.domain.system.repository

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.shopping.admin.domain.system.dto.ScreenDto
import com.shopping.admin.domain.system.entity.QScreen.screen
import com.shopping.admin.domain.system.entity.Screen
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ScreenRepository : JpaRepository<Screen, Long>, ScreenRepositoryCustom {

    fun findScreenById(id: Long): Screen

//    override fun findAll(): List<Screen>

    @Query("select new com.shopping.admin.domain.system.dto.ScreenDto(s.id, s.name, s.path, s.useYn) from Screen s where s.id = :id")
    fun findScreenDtoById(@Param("id") id: Long): ScreenDto

}

interface ScreenRepositoryCustom {

    fun findScreens(dto: ScreenDto): List<ScreenDto>
}

class ScreenRepositoryCustomImpl(
    private val jPAQueryFactory: JPAQueryFactory
) : ScreenRepositoryCustom {

    override fun findScreens(dto: ScreenDto): List<ScreenDto> {
        return jPAQueryFactory
            .select(
                Projections.constructor(
                    ScreenDto::class.java,
                    screen.id,
                    screen.name,
                    screen.path,
                    screen.useYn
                )
            )
            .from(screen)
            .where(
                nameLike(dto.name),
                useYnEq(dto.useYn)
            )
            .fetch()
    }

    // 동적쿼리
    private fun nameLike(name: String?): BooleanExpression? {
        return if (name.isNullOrBlank()) null else screen.name.like("%${name}%")
    }

    // 동적쿼리
    private fun useYnEq(useYn: String?): BooleanExpression? {
        return if (useYn.isNullOrBlank()) null else screen.useYn.eq(useYn)
    }
}