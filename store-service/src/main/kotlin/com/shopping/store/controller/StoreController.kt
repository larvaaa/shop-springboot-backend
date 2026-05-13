package com.shopping.store.controller

import com.common.core.api.ApiResponse
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
        TODO()
    }

    @GetMapping
    fun getStores(): ResponseEntity<Any> {
        TODO()
    }

    // 가게등록
    @PostMapping
    fun registerStore(@RequestBody dto: StoreRegisterRequest): ResponseEntity<Any> {
        storeService.registerStore(dto)

        return ResponseEntity.ok(ApiResponse.success())
    }

    @PutMapping("/{id}")
    fun updateStore(
        @PathVariable id: Long,
        @RequestBody request: Any
    ): ResponseEntity<Any> {
        TODO()
    }
}
