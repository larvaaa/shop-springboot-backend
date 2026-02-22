package com.shopping.admin.domain.system.controller

import com.shopping.admin.domain.system.dto.MenuDto
import com.shopping.admin.domain.system.service.MenuService
import org.apache.coyote.Response
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/system")
class MenuController (
    val menuService: MenuService
) {

    @PostMapping("/menu")
    fun saveMenu(@RequestBody menuDto: MenuDto): ResponseEntity<String> {

        menuService.saveMenu(menuDto)
        return ResponseEntity.ok("success")
    }

    @GetMapping("/menu")
    fun selectMenu(): ResponseEntity<List<MenuDto>> {

        return ResponseEntity.ok(menuService.findAll())
    }

    @DeleteMapping("/menu")
    fun deleteMenu(@RequestBody menuDto: MenuDto): ResponseEntity<String> {

        menuService.deleteMenu(menuDto)
        return ResponseEntity.ok("success")
    }
}