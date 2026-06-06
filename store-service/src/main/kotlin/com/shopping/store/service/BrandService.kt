package com.shopping.store.service

import com.shopping.store.dto.BrandFindRequest
import com.shopping.store.dto.BrandFindResponse
import com.shopping.store.dto.BrandRegisterRequest
import com.shopping.store.entity.Brand
import com.shopping.store.repository.BrandRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BrandService(
    private val brandRepository: BrandRepository
) {

    fun registerBrand(dto: BrandRegisterRequest) {

        val brand = Brand(
            name = dto.name,
            description = dto.description,
            attachFileId = dto.attachFileId,
            isUse = dto.isUse,
        )

        brandRepository.save(brand)
    }

    @Transactional
    fun updateBrand(id: Long, dto: BrandRegisterRequest) {
        val findBrand = brandRepository.findEntityById(id) ?: throw IllegalStateException("존재하지 않는 id => ${id}")
        findBrand.name = dto.name
        findBrand.description = dto.description ?: findBrand.description
        findBrand.attachFileId = dto.attachFileId
        findBrand.isUse = dto.isUse
    }

    fun findBrands(dto: BrandFindRequest): List<BrandFindResponse> {
        return brandRepository.findBrands(dto)
    }

    fun findBrand(id: Long): BrandFindResponse {
        return brandRepository.findBrandById(id)
    }

}