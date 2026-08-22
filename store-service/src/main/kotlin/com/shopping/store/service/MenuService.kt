package com.shopping.store.service

import com.shopping.store.dto.MenuDetailResponse
import com.shopping.store.dto.MenuFindResponse
import com.shopping.store.dto.MenuRegisterRequest
import com.shopping.store.dto.OptionDetailDto
import com.shopping.store.dto.OptionGroupDto
import com.shopping.store.entity.Menu
import com.shopping.store.entity.OptionDetail
import com.shopping.store.entity.OptionGroup
import com.shopping.store.repository.MenuRepository
import com.shopping.store.repository.OptionDetailRepository
import com.shopping.store.repository.OptionGroupRepository
import com.shopping.store.repository.StoreRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MenuService(
    private val menuRepository: MenuRepository,
    private val storeRepository: StoreRepository,
    private val optionGroupRepository: OptionGroupRepository,
    private val optionDetailRepository: OptionDetailRepository,
) {

    @Transactional(readOnly = true)
    fun getMenus(storeId: Long, searchKeyword: String?): List<MenuFindResponse> {
        val menus = menuRepository.findAllByStoreIdOrderBySortOrder(storeId)
        val filtered = if (searchKeyword.isNullOrBlank()) {
            menus
        } else {
            menus.filter { it.name.contains(searchKeyword) }
        }
        return filtered.map {
            MenuFindResponse(
                id = it.id,
                name = it.name,
                price = it.price,
                attachFileId = it.attachFileId,
                isUse = it.isUse,
                isSoldOut = it.isSoldOut,
                sortOrder = it.sortOrder,
            )
        }
    }

    @Transactional(readOnly = true)
    fun getMenu(id: Long): MenuDetailResponse {
        val menu = menuRepository.findMenuById(id)
        val groupDtos = optionGroupRepository.findAllByMenuOrderBySortOrder(menu).map { g ->
            OptionGroupDto(
                id = g.id,
                name = g.name,
                isRequired = g.isRequired,
                isMultiSelectedEnabled = g.isMultiSelectedEnabled,
                sortOrder = g.sortOrder,
                options = optionDetailRepository.findAllByOptionGroupOrderBySortOrder(g).map {
                    OptionDetailDto(
                        id = it.id,
                        name = it.name,
                        extraPrice = it.extraPrice,
                        isUse = it.isUse,
                        sortOrder = it.sortOrder,
                    )
                },
            )
        }
        return MenuDetailResponse(
            id = menu.id,
            storeId = menu.store.id!!,
            name = menu.name,
            description = menu.description,
            price = menu.price,
            attachFileId = menu.attachFileId,
            isUse = menu.isUse,
            isSoldOut = menu.isSoldOut,
            sortOrder = menu.sortOrder,
            optionGroups = groupDtos,
        )
    }

    fun registerMenu(dto: MenuRegisterRequest) {
        val store = storeRepository.findStoreById(dto.storeId)
        val menu = Menu(
            store = store,
            name = dto.name,
            description = dto.description,
            price = dto.price,
            attachFileId = dto.attachFileId,
            isUse = dto.isUse,
            isSoldOut = dto.isSoldOut,
            sortOrder = dto.sortOrder,
        )
        menuRepository.save(menu)
        syncOptionGroups(menu, dto.optionGroups)
    }

    fun updateMenu(id: Long, dto: MenuRegisterRequest) {
        val menu = menuRepository.findMenuById(id)
        menu.name = dto.name
        menu.description = dto.description
        menu.price = dto.price
        menu.attachFileId = dto.attachFileId
        menu.isUse = dto.isUse
        menu.isSoldOut = dto.isSoldOut
        menu.sortOrder = dto.sortOrder

        syncOptionGroups(menu, dto.optionGroups)
    }

    fun deleteMenu(id: Long) {
        val menu = menuRepository.findMenuById(id)
        optionGroupRepository.findAllByMenuOrderBySortOrder(menu).forEach { group ->
            optionDetailRepository.deleteByOptionGroup(group)
            optionGroupRepository.delete(group)
        }
        menuRepository.delete(menu)
    }

    // 기존 id가 요청에 남아있으면 수정, 없으면 신규 삽입, 요청에서 빠진 기존 row는 삭제 — PK를 최대한 유지
    private fun syncOptionGroups(menu: Menu, groupDtos: List<OptionGroupDto>) {
        val existingGroups: List<OptionGroup> = optionGroupRepository.findAllByMenuOrderBySortOrder(menu)
        val existingGroupById: Map<Long?, OptionGroup> = existingGroups.associateBy { it.id }
        val incomingGroupIds: Set<Long> = groupDtos.mapNotNull { it.id }.toSet()

        existingGroups.filter { it.id !in incomingGroupIds }.forEach { group ->
            optionDetailRepository.deleteByOptionGroup(group)
            optionGroupRepository.delete(group)
        }

        groupDtos.forEach { g ->
            val group = existingGroupById[g.id]
                ?.apply {
                    name = g.name
                    isRequired = g.isRequired
                    isMultiSelectedEnabled = g.isMultiSelectedEnabled
                    sortOrder = g.sortOrder
                }
                ?: optionGroupRepository.save(
                    OptionGroup(
                        menu = menu,
                        name = g.name,
                        isRequired = g.isRequired,
                        isMultiSelectedEnabled = g.isMultiSelectedEnabled,
                        sortOrder = g.sortOrder,
                    ),
                )
            syncOptions(group, g.options)
        }
    }

    private fun syncOptions(group: OptionGroup, optionDtos: List<OptionDetailDto>) {
        val existingOptions = optionDetailRepository.findAllByOptionGroupOrderBySortOrder(group)
        val existingOptionById = existingOptions.associateBy { it.id }
        val incomingOptionIds = optionDtos.mapNotNull { it.id }.toSet()

        existingOptions.filter { it.id !in incomingOptionIds }
            .forEach { optionDetailRepository.delete(it) }

        optionDtos.forEach { o ->
            existingOptionById[o.id]
                ?.apply {
                    name = o.name
                    extraPrice = o.extraPrice
                    isUse = o.isUse
                    sortOrder = o.sortOrder
                }
                ?: optionDetailRepository.save(
                    OptionDetail(
                        optionGroup = group,
                        name = o.name,
                        extraPrice = o.extraPrice,
                        isUse = o.isUse,
                        sortOrder = o.sortOrder,
                    ),
                )
        }
    }
}
