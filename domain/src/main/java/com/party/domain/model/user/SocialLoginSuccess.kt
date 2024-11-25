package com.party.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
sealed class SocialLogin

@Serializable
data class SocialLoginSuccess(
    val accessToken: String,
    val refreshToken: String,
): SocialLogin()

@Serializable
data class SocialLoginError(
    val message: String,
    val signupAccessToken: String,
    var userEmail: String? = null,
): SocialLogin()

@Serializable
data class SocialLoginException(
    val code: String,
): SocialLogin()

