package com.party.data.dto.user.detail

import kotlinx.serialization.Serializable

@Serializable
data class SaveCarrierDto(
    val career : List<SaveCarrierItemDto> = emptyList()
)

@Serializable
data class SaveCarrierItemDto(
    val id: Int,
    val positionId: Int,
    val years: Int,
    val careerType: String,
)
