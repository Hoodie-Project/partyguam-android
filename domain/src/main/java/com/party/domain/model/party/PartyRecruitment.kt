package com.party.domain.model.party

data class PartyRecruitment(
    val partyRecruitmentId: Int,
    val main: String,
    val sub: String,
    val content: String,
    val recruitingCount: Int,
    val recruitedCount: Int,
    val applicationCount: Int,
    val createdAt: String,
)
