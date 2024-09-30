package com.party.domain.model.user

import kotlinx.serialization.Serializable

interface SocialLoginResponse

@Serializable
data class SocialLoginSuccessResponse(
    val accessToken: String,
    val refreshToken: String,
): SocialLoginResponse

@Serializable
data class SocialLoginErrorResponse(
    val message: String,
    val signupAccessToken: String,
    var userEmail: String? = null,
): SocialLoginResponse

@Serializable
data class SocialLoginExceptionResponse(
    val code: String,
): SocialLoginResponse

