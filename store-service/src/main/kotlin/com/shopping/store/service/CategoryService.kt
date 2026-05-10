package com.shopping.store.service

import com.shopping.store.dto.CategoryRegisterRequest
import com.shopping.store.dto.CategoryResponse
import com.shopping.store.dto.CategoryUpdateRequest
import com.shopping.store.entity.Category
import com.shopping.store.repository.CategoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CategoryService(
    private val categoryRepository: CategoryRepository
) {

    @Transactional(readOnly = true)
    fun getCategory(id: Long) {
        TODO()
    }

    @Transactional(readOnly = true)
    fun getCategories(): List<CategoryResponse> {
        return categoryRepository.findAllByOrderBySortOrder().map {
            CategoryResponse(
                id = it.id,
                name = it.name,
                parentId = it.parentId?.id,
                sortOrder = it.sortOrder,
                depth = it.depth,
                isUse = it.isUse,
            )
        }
    }

    fun registerCategory(request: CategoryRegisterRequest) {
        val parent = request.parentId?.let { categoryRepository.findById(it).orElseThrow() }

        val category = Category(
            name = request.name,
            parentId = parent,
            sortOrder = request.sortOrder,
            depth = request.depth,
            isUse = request.isUse,
        )

        categoryRepository.save(category)
    }

    fun deleteCategory(id: Long) {
        val category = categoryRepository.findById(id).orElseThrow()
        categoryRepository.delete(category)
    }

    fun updateCategory(id: Long, request: CategoryUpdateRequest) {
        val category = categoryRepository.findById(id).orElseThrow()
        val parent = request.parentId?.let { categoryRepository.findById(it).orElseThrow() }

        category.name = request.name
        category.parentId = parent
        category.sortOrder = request.sortOrder
        category.isUse = request.isUse
    }
}
