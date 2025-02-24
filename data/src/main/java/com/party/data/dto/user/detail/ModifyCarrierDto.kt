package com.party.data.dto.user.detail

import kotlinx.serialization.Serializable

@Serializable
data class ModifyCarrierDto(
    val career: List<ModifyCarrierItem> = emptyList()
)

@Serializable
data class ModifyCarrierItem(
    val positionId: Int,
    val years: Int,
    val careerType: String,
)