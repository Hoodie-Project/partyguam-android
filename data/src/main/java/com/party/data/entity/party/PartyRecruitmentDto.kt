package com.party.data.entity.party

import kotlinx.serialization.Serializable

@Serializable
data class PartyRecruitmentDto(
    val partyRecruitmentId: Int,
    val main: String,
    val sub: String,
    val content: String,
    val recruitingCount: Int,
    val recruitedCount: Int,
    val applicationCount: Int,
    val createdAt: String,
)
