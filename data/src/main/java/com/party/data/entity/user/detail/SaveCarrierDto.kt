package com.party.data.entity.user.detail

import kotlinx.serialization.Serializable

@Serializable
data class SaveCarrierEntity1(
    val career : List<SaveCarrierEntity> = emptyList()
)

@Serializable
data class SaveCarrierEntity(
    //val id: Int,
    //val userId: Int,
    val positionId: Int,
    val years: Int,
    val careerType: String,
)
