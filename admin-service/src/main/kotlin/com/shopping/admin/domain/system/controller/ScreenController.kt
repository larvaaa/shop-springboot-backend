package com.shopping.admin.domain.system.controller

import com.common.core.api.ApiResponse
import com.shopping.admin.domain.system.dto.ScreenDto
import com.shopping.admin.domain.system.service.ScreenService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ScreenController(
    val screenService: ScreenService
) {

    @PostMapping("/screen")
    fun createScreen(@RequestBody dto: ScreenDto): ResponseEntity<String> {
        screenService.createScreen(dto)
        return ResponseEntity.ok("success")
    }

    @PatchMapping("/screen/{id}")
    fun updateScreen(@PathVariable id: Long, @RequestBody dto: ScreenDto): ResponseEntity<ApiResponse<Unit>> {
        screenService.updateScreen(id, dto)
        return ResponseEntity.ok(ApiResponse.success())
    }

    @GetMapping("/screen")
    fun selectScreen(dto: ScreenDto): ResponseEntity<List<ScreenDto>> {
        val screenList = screenService.getScreen(dto)
        return ResponseEntity.ok(screenList)
    }

    @GetMapping("/screen/{id}")
    fun selectScreen(@PathVariable id: Long): ResponseEntity<ScreenDto> {
        val screen: ScreenDto = screenService.getScreenDtoById(id)
        return ResponseEntity.ok(screen)
    }

    @DeleteMapping("/screen/{id}")
    fun deleteScreen(@PathVariable id: Long): ResponseEntity<ApiResponse<Unit>> {
        screenService.deleteScreen(id)
        return ResponseEntity.ok(ApiResponse.success())
    }
}