package com.party.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
data class SocialLoginRequest(
    val uid: String,
    val idToken: String,
)
