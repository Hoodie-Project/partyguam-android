package com.party.common

import kotlinx.serialization.Serializable

sealed interface ServerApiResponse {
    @Serializable
    data class BaseSuccessResponse<T>(
        val code: String? = null,
        val message: String? = null,
        val data: T? = null,
    ): ServerApiResponse

    @Serializable
    data class BaseErrorResponse<T>(
        val message: String? = null,
        val error: String? = null,
        val statusCode: Int? = null,
        val path: String? = null,
        val timestamp: String? = null,
        val data: T? = null,
    ): ServerApiResponse

    @Serializable
    data class BaseExceptionResponse(
        val message: String? = null,
    ): ServerApiResponse
}