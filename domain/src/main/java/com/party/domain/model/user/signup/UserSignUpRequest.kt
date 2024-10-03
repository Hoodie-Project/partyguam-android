package com.party.domain.model.user.signup

import kotlinx.serialization.Serializable

@Serializable
data class UserSignUpRequest(
    val email: String,
    val nickname: String,
    val birth: String,
    val gender: String,
)