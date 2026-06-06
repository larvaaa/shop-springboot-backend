package com.shopping.admin.domain.system.repository

import com.shopping.admin.domain.system.dto.MenuProjection
import com.shopping.admin.domain.system.entity.Menu
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MenuRepository : JpaRepository<Menu, Long>, MenuRepositoryCustom {

    fun findMenuById(id: Long): Menu

    @Query("""
        with recursive menu_hierarchy as (
            select m.menu_id
                 , m.menu_name
                 , p.menu_name as parent_menu_name
                 , m.screen_id
                 , m.level
                 , m.sort
                 , m.parent_id
                 , m.use_yn
                 , s.screen_name
                 , s.path as screen_path
                 , m.created_by
                 , m.created_date
                 , m.last_modified_by
                 , m.last_modified_date
                 , cast(m.sort as char) as order_path
              from menu m
              left join screen s
                on m.screen_id = s.screen_id
              left join menu p
                on m.parent_id = p.menu_id 
             where m.level = 1
            union all
            select m.menu_id
                 , m.menu_name
                 , p.menu_name as parent_menu_name
                 , m.screen_id
                 , m.level
                 , m.sort
                 , m.parent_id
                 , m.use_yn
                 , s.screen_name
                 , s.path as screen_path
                 , m.created_by
                 , m.created_date
                 , m.last_modified_by
                 , m.last_modified_date
                 , concat(mh.order_path, '.', m.sort) as order_path
              from menu m
              left join screen s
                on m.screen_id = s.screen_id
              left join menu p
                on m.parent_id = p.menu_id 
             inner join menu_hierarchy mh 
                on mh.menu_id = m.parent_id
        )
        select menu_id
             , menu_name
             , parent_menu_name
             , level
             , sort
             , use_yn
             , parent_id
             , screen_id
             , screen_name
             , screen_path
             , created_by
             , created_date
             , last_modified_by
             , last_modified_date
          from menu_hierarchy
         order by order_path
    """, nativeQuery = true)
    // JPA 네이티브 쿼리는 일반 클래스로 직접 매핑 불가 — 프로젝션 인터페이스만 지원
    fun findHierarchyMenu(): List<MenuProjection>

    override fun delete(menu: Menu)
}

interface MenuRepositoryCustom {


}

class MenuRepositoryCustomImpl : MenuRepositoryCustom {


}
