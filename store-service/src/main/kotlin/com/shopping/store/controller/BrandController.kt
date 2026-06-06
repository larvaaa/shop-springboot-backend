package com.shopping.store.controller

import com.common.core.api.ApiResponse
import com.shopping.store.dto.BrandFindRequest
import com.shopping.store.dto.BrandFindResponse
import com.shopping.store.dto.BrandRegisterRequest
import com.shopping.store.service.BrandService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/brand")
class BrandController(
    private val brandService: BrandService
) {

    @PostMapping
    fun registerBrand(
        // @ModelAttribute 는 생략가능하지만 명시적으로 적어주는 것이 좋다
        // MultipartFile을 받기위해서 사용
        @ModelAttribute dto: BrandRegisterRequest,
    ): ResponseEntity<ApiResponse<Unit>> {
        brandService.registerBrand(dto)
        return ResponseEntity.ok(ApiResponse.success())
    }

    @PatchMapping("/{id}")
    fun updateBrand(
        @PathVariable id: Long,
        @ModelAttribute dto: BrandRegisterRequest
    ): ResponseEntity<ApiResponse<Unit>> {
        brandService.updateBrand(id, dto)
        return ResponseEntity.ok(ApiResponse.success())
    }

    @GetMapping
    fun findBrands(@ModelAttribute dto: BrandFindRequest): ResponseEntity<ApiResponse<List<BrandFindResponse>>> {
        val findResults = brandService.findBrands(dto)
        return ResponseEntity.ok(ApiResponse.success(findResults))
    }

    @GetMapping("/{id}")
    fun findBrand(@PathVariable("id") id: Long): ResponseEntity<ApiResponse<BrandFindResponse>> {
        val findResult = brandService.findBrand(id)
        return ResponseEntity.ok(ApiResponse.success(findResult))
    }
}