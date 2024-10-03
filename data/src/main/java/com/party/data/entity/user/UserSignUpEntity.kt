package com.party.data.entity.user

import kotlinx.serialization.Serializable

@Serializable
data class UserSignUpEntity(
    val accessToken: String,
)
