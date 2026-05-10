package com.shopping.store.controller

import com.common.core.api.ApiResponse
import com.shopping.store.dto.CategoryRegisterRequest
import com.shopping.store.dto.CategoryResponse
import com.shopping.store.dto.CategoryUpdateRequest
import com.shopping.store.service.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/category")
class CategoryController(
    private val categoryService: CategoryService
) {

    @GetMapping("/{id}")
    fun getCategory(@PathVariable id: Long): ResponseEntity<Any> {
        TODO()
    }

    @GetMapping
    fun getCategories(): ResponseEntity<ApiResponse<List<CategoryResponse>>> {
        return ResponseEntity.ok(ApiResponse.success(categoryService.getCategories()))
    }

    @PostMapping
    fun registerCategory(@RequestBody request: CategoryRegisterRequest): ResponseEntity<ApiResponse<Unit>> {
        categoryService.registerCategory(request)
        return ResponseEntity.ok(ApiResponse.success())
    }

    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: Long): ResponseEntity<ApiResponse<Unit>> {
        categoryService.deleteCategory(id)
        return ResponseEntity.ok(ApiResponse.success())
    }

    @PatchMapping("/{id}")
    fun updateCategory(
        @PathVariable id: Long,
        @RequestBody request: CategoryUpdateRequest
    ): ResponseEntity<ApiResponse<Unit>> {
        categoryService.updateCategory(id, request)
        return ResponseEntity.ok(ApiResponse.success())
    }
}
