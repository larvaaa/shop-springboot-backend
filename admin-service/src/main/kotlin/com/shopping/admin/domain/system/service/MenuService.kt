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

    fun findAll(): List<MenuDto> {

        val findMenus: List<Menu> = menuRepository.findHierarchyMenu()

        val findMenuDtos = findMenus.map {
            MenuDto(
                menuId = it.id,
                menuName = it.name,
                level = it.level,
                sort = it.sort,
                useYn = it.useYn,
                parentId = it.parent?.id,
                screenId = it.screen?.id,
                screenName = it.screen?.name,
                screenPath = it.screen?.path
            )
        }

        return findMenuDtos

    }

    fun deleteMenu(dto: MenuDto) {

        val menu = menuRepository.findMenuById(requireNotNull(dto.menuId))

        menuRepository.delete(menu)
    }

}

