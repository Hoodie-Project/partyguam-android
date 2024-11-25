package com.party.data.entity.user.detail

import kotlinx.serialization.Serializable

@Serializable
data class SaveCarrierDto(
    val career : List<SaveCarrierItem> = emptyList()
)

@Serializable
data class SaveCarrierItem(
    //val id: Int,
    //val userId: Int,
    val positionId: Int,
    val years: Int,
    val careerType: String,
)
