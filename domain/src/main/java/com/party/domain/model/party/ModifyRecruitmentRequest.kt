package com.party.domain.model.party

import kotlinx.serialization.Serializable

@Serializable
data class ModifyRecruitmentRequest(
    val positionId: Int,
    val content: String,
    val recruiting_count: Int,
)