package com.party.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
data class CheckNickNameResponse(
    val message: String,
)