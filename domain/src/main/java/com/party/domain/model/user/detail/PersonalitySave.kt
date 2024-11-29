package com.party.domain.model.user.detail

import kotlinx.serialization.Serializable

@Serializable
data class PersonalitySave(
    val id: Int,
    val userId: Int,
    val personalityOptionId: Int,
)