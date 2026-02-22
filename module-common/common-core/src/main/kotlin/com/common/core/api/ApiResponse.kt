package com.common.core.api

data class ApiResponse<T> (
    val code: String,
    val message: String,
    val data: T? = null
) {
    companion object {
        fun <T> success(data: T): ApiResponse<T> {
            return ApiResponse(
                code = "success",
                message = "성공",
                data = data
            )
        }

        fun success(): ApiResponse<Unit> {
            return ApiResponse(
                code = "success",
                message = "성공",
                data = null
            )
        }

        fun error(code: String, message: String): ApiResponse<Nothing> {
            return ApiResponse(
                code = code,
                message = message,
                data = null
            )
        }
    }
}