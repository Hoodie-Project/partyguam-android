package com.party.data.dto.user.detail

import kotlinx.serialization.Serializable

@Serializable
data class GetCarrierDto(
    val id: Int,
    val positionId: Int,
    val years: Int,
    val careerType: String,
)
