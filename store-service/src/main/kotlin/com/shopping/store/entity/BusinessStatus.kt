package com.shopping.store.entity

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

enum class BusinessStatus(val code: Int) {
    OPEN(1),
    CLOSED(2),
    PREPARING(3),
    SUSPENDED(4),
}

// db에는 1,2,3,4로 저장 하기위한 설정
@Converter(autoApply = true)
class BusinessStatusConverter : AttributeConverter<BusinessStatus, Int> {
    override fun convertToDatabaseColumn(attribute: BusinessStatus): Int = attribute.code
    override fun convertToEntityAttribute(dbData: Int): BusinessStatus =
        BusinessStatus.entries.first { it.code == dbData }
}
