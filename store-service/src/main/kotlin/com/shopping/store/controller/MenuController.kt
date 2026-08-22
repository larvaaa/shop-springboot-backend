package com.shopping.store.controller

import com.common.core.api.ApiResponse
import com.shopping.store.dto.MenuDetailResponse
import com.shopping.store.dto.MenuFindResponse
import com.shopping.store.dto.MenuRegisterRequest
import com.shopping.store.service.MenuService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/menu")
class MenuController(
    private val menuService: MenuService
) {

    @GetMapping
    fun getMenus(
        @RequestParam storeId: Long,
        @RequestParam(required = false) searchKeyword: String?,
    ): ResponseEntity<ApiResponse<List<MenuFindResponse>>> {
        return ResponseEntity.ok(ApiResponse.success(menuService.getMenus(storeId, searchKeyword)))
    }

    @GetMapping("/{id}")
    fun getMenu(@PathVariable id: Long): ResponseEntity<ApiResponse<MenuDetailResponse>> {
        return ResponseEntity.ok(ApiResponse.success(menuService.getMenu(id)))
    }

    // 메뉴등록 (옵션그룹/옵션 포함)
    @PostMapping
    fun registerMenu(@RequestBody dto: MenuRegisterRequest): ResponseEntity<ApiResponse<Unit>> {
        menuService.registerMenu(dto)
        return ResponseEntity.ok(ApiResponse.success())
    }

    // 메뉴수정 (옵션그룹/옵션 통째로 재생성)
    @PatchMapping("/{id}")
    fun updateMenu(
        @PathVariable id: Long,
        @RequestBody dto: MenuRegisterRequest,
    ): ResponseEntity<ApiResponse<Unit>> {
        menuService.updateMenu(id, dto)
        return ResponseEntity.ok(ApiResponse.success())
    }

    @DeleteMapping("/{id}")
    fun deleteMenu(@PathVariable id: Long): ResponseEntity<ApiResponse<Unit>> {
        menuService.deleteMenu(id)
        return ResponseEntity.ok(ApiResponse.success())
    }
}
