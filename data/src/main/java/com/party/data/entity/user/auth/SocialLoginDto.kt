package com.party.data.entity.user.auth

import kotlinx.serialization.Serializable

@Serializable
sealed class SocialLoginDto

@Serializable
data class SocialLoginSuccessDto(
    val accessToken: String,
    val refreshToken: String,
): SocialLoginDto()

@Serializable
data class SocialLoginErrorDto(
    val message: String,
    val signupAccessToken: String,
): SocialLoginDto()

