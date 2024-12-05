package com.party.data.dto.user.detail

import kotlinx.serialization.Serializable

@Serializable
data class PositionListDto(
    val id: Int,
    val main: String,
    val sub: String,
)
