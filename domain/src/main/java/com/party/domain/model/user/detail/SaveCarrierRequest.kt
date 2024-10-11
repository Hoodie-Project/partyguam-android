package com.party.domain.model.user.detail

import kotlinx.serialization.Serializable

@Serializable
data class SaveCarrierList(
    val career: List<SaveCarrierRequest>,
)

@Serializable
data class SaveCarrierRequest(
    val positionId: Int,
    val years: Int,
    val careerType: String,
)
