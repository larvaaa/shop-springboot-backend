package com.shopping.store.controller

import com.common.core.api.ApiResponse
import com.shopping.store.dto.StoreFindRequest
import com.shopping.store.dto.StoreRegisterRequest
import com.shopping.store.service.StoreService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/store")
class StoreController(
    private val storeService: StoreService
) {

    @GetMapping("/{id}")
    fun getStore(@PathVariable id: Long): ResponseEntity<Any> {
        return ResponseEntity.ok(ApiResponse.success(storeService.getStore(id)))
    }

    @GetMapping
    fun getStores(dto: StoreFindRequest): ResponseEntity<Any> {
        return ResponseEntity.ok(ApiResponse.success(storeService.getStores(dto)))
    }

    // 가게등록
    @PostMapping
    fun registerStore(@RequestBody dto: StoreRegisterRequest): ResponseEntity<Any> {

        try {
            storeService.registerStore(dto)

        } catch (e: IllegalStateException) {
            return ResponseEntity.ok(ApiResponse.error("fail", e.message ?: "문제가 발생했습니다. \n 잠시후 다시 시도해주세요"))
        }


        return ResponseEntity.ok(ApiResponse.success())
    }

    // 가게수정
    @PatchMapping("/{id}")
    fun updateStore(
        @PathVariable id: Long,
        @RequestBody dto: StoreRegisterRequest
    ): ResponseEntity<Any> {

        try {
            storeService.updateStore(id, dto)

        } catch (e: IllegalStateException) {
            return ResponseEntity.ok(ApiResponse.error("fail", e.message ?: "문제가 발생했습니다. \n 잠시후 다시 시도해주세요"))
        }


        return ResponseEntity.ok(ApiResponse.success())
    }
}
