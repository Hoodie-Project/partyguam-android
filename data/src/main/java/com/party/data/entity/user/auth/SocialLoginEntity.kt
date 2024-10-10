package com.party.data.entity.user.auth

import kotlinx.serialization.Serializable

@Serializable
sealed class SocialLoginEntity

@Serializable
data class SocialLoginSuccessEntity(
    val accessToken: String,
    val refreshToken: String,
): SocialLoginEntity()

@Serializable
data class SocialLoginErrorEntity(
    val message: String,
    val signupAccessToken: String,
): SocialLoginEntity()

