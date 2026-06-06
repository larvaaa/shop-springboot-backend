package com.shopping.admin.domain.system.service


import com.shopping.admin.domain.system.dto.MenuDto
import com.shopping.admin.domain.system.entity.Menu
import com.shopping.admin.domain.system.repository.MenuRepository
import com.shopping.admin.domain.system.repository.ScreenRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MenuService (
    val menuRepository: MenuRepository,
    val screenRepository: ScreenRepository
) {

    fun saveMenu(menuDto: MenuDto): Menu {

        val findMenu = if(menuDto.menuId == null) null else menuRepository.findMenuById(menuDto.menuId)
        val findParent = if(menuDto.parentId == null) null else menuRepository.findMenuById(menuDto.parentId)
        val findScreen = if(menuDto.screenId == null) null else screenRepository.findScreenById(menuDto.screenId)

        if(findMenu != null) {

            findMenu.name = menuDto.menuName
            findMenu.screen = findScreen
            findMenu.sort = menuDto.sort
            findMenu.useYn = menuDto.useYn

            return findMenu
        } else {

            val menu = Menu(
                id = menuDto.menuId,
                name = menuDto.menuName,
                screen = findScreen,
                level = menuDto.level,
                sort = menuDto.sort,
                parent = findParent,
                useYn = menuDto.useYn,
            )

            menuRepository.save(menu)

            return menu
        }
    }

    fun findHierarchyMenu(): List<MenuDto> {
        return menuRepository.findHierarchyMenu().map {
            MenuDto(
                menuId = it.getMenuId(),
                menuName = it.getMenuName(),
                parentMenuName = it.getParentMenuName() ?: "",
                level = it.getLevel(),
                sort = it.getSort(),
                useYn = it.getUseYn().first(),
                parentId = it.getParentId(),
                screenId = it.getScreenId(),
                screenName = it.getScreenName(),
                screenPath = it.getScreenPath(),
                createdBy = it.getCreatedBy(),
                createdDate = it.getCreatedDate(),
                lastModifiedBy = it.getLastModifiedBy(),
                lastModifiedDate = it.getLastModifiedDate(),
            )
        }
    }

    fun deleteMenu(dto: MenuDto) {

        val menu = menuRepository.findMenuById(requireNotNull(dto.menuId))

        menuRepository.delete(menu)
    }

}

