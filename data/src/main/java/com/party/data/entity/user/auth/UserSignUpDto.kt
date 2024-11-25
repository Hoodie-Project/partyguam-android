package com.party.data.entity.user.auth

import kotlinx.serialization.Serializable

@Serializable
data class UserSignUpDto(
    val accessToken: String,
)
