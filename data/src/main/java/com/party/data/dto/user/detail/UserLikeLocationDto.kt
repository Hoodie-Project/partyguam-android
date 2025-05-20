package com.party.data.dto.user.detail

import kotlinx.serialization.Serializable

@Serializable
data class UserLikeLocationDto(
    val id: Int,
    val userId: Int,
)
