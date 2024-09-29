package com.party.common

import kotlinx.serialization.Serializable

@Serializable
sealed interface ServerApiResponse {

    @Serializable
    data class SuccessResponse<T>(
        val data: T? = null,
    ): ServerApiResponse

    @Serializable
    data class ErrorResponse<T>(
        val data: T? = null,
        val message: String? = null,
        val error: String? = null,
        val statusCode: Int? = null,
        val path: String? = null,
        val timestamp: String? = null,
    ): ServerApiResponse

    @Serializable
    data class ExceptionResponse(
        val message: String? = null,
    ): ServerApiResponse
}