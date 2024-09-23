package com.party.data.entity.user

import kotlinx.serialization.Serializable

@Serializable
data class SocialLoginEntity(
    val accessToken: String,
    val refreshToken: String,
)

@Serializable
data class SocialLoginErrorEntity(
    val message: String,
    val signupAccessToken: String,
)