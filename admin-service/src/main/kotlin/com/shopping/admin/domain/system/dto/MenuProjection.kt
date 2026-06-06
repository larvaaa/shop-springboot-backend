package com.shopping.admin.domain.system.dto

import java.time.LocalDateTime

interface MenuProjection {
    fun getMenuId(): Long?
    fun getMenuName(): String
    fun getParentMenuName(): String?
    fun getLevel(): Int
    fun getSort(): Int
    fun getUseYn(): String
    fun getParentId(): Long?
    fun getScreenId(): Long?
    fun getScreenName(): String?
    fun getScreenPath(): String?
    fun getCreatedBy(): String?
    fun getCreatedDate(): LocalDateTime?
    fun getLastModifiedBy(): String?
    fun getLastModifiedDate(): LocalDateTime?
}
