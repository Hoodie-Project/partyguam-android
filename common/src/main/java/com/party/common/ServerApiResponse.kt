package com.party.common

import kotlinx.serialization.Serializable

sealed interface ServerApiResponse<T> {

    @Serializable
    data class SuccessResponse<T>(
        val data: T? = null,
    ): ServerApiResponse<T>

    @Serializable
    data class ErrorResponse<T>(
        val data: T? = null,
        val message: String? = null,
        val error: String? = null,
        val statusCode: Int? = null,
        val path: String? = null,
        val timestamp: String? = null,
        val recoverAccessToken: String? = null,
        val email: String? = null,
        val deletedAt: String? = null,
    ): ServerApiResponse<T>

    @Serializable
    data class ExceptionResponse<T>(
        val message: String? = null,
    ): ServerApiResponse<T>
}