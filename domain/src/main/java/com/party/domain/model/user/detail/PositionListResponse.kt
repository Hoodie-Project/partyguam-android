package com.party.domain.model.user.detail

import kotlinx.serialization.Serializable

@Serializable
data class PositionListResponse(
    val id: Int,
    val main: String,
    val sub: String,
)
