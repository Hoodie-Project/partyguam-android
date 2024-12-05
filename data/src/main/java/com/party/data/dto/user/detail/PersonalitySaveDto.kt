package com.party.data.dto.user.detail

import kotlinx.serialization.Serializable

@Serializable
data class PersonalitySaveDto(
    val id: Int,
    val userId: Int,
    val personalityOptionId: Int,
)