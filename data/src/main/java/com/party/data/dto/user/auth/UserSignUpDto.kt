package com.party.data.dto.user.auth

import kotlinx.serialization.Serializable

@Serializable
data class UserSignUpDto(
    val accessToken: String,
)
