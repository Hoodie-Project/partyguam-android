package com.party.data.entity.user.detail

import kotlinx.serialization.Serializable

@Serializable
data class PersonalitySaveDto(
    val id: Int,
    val userId: Int,
    val personalityOptionId: Int,
)