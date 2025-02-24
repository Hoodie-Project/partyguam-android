package com.party.domain.model.user.detail

import kotlinx.serialization.Serializable

@Serializable
data class ModifyCarrierList(
    val career: List<ModifyCarrierRequest>
)

@Serializable
data class ModifyCarrierRequest(
    val positionId: Int,
    val years: Int,
    val careerType: String,
)