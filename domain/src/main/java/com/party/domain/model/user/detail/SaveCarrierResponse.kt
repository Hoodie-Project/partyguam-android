package com.party.domain.model.user.detail

import kotlinx.serialization.Serializable

@Serializable
data class SaveCarrierResponse(
    //val id: Int,
    //val userId: Int,
    val positionId: Int,
    val years: Int,
    val careerType: String,
)
