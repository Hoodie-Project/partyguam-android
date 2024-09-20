package com.party.domain.model.member

import kotlinx.serialization.Serializable

@Serializable
data class SocialLoginResponse(
    val accessToken: String,
    val message: String? = null,
    val error: String? = null,
    val statusCode: String? = null,
    val path: String? = null,
    val timestamp: String? = null,
)
