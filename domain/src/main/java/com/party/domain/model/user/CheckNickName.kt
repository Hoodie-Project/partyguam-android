package com.party.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
data class CheckNickName(
    val description: String,
)