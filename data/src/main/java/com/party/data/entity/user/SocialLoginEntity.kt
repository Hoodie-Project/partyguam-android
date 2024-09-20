package com.party.data.entity.user

import kotlinx.serialization.Serializable

@Serializable
data class SocialLoginEntity(
    val accessToken: String,
)
