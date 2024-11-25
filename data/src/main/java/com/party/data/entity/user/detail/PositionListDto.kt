package com.party.data.entity.user.detail

import kotlinx.serialization.Serializable

@Serializable
data class PositionListDto(
    val id: Int,
    val main: String,
    val sub: String,
)
