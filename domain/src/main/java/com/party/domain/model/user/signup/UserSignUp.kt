package com.party.domain.model.user.signup

import kotlinx.serialization.Serializable

@Serializable
data class UserSignUp(
    val accessToken: String
)
