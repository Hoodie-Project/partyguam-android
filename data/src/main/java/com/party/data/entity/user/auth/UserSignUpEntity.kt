package com.party.data.entity.user.auth

import kotlinx.serialization.Serializable

@Serializable
data class UserSignUpEntity(
    val accessToken: String,
)
