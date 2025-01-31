package com.party.data.dto.user.auth

import kotlinx.serialization.Serializable

@Serializable
data class MySocialOauthDto(
    val provider: String,
)