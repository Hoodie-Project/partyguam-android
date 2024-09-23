package com.party.domain.model.member

import kotlinx.serialization.Serializable

@Serializable
data class SocialLoginResponse(
    val accessToken: String,
    val refreshToken: String,
)

@Serializable
data class SocialLoginErrorResponse(
    val message: String,
    val signupAccessToken: String,
)
