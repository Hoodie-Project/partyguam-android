package com.party.domain.model.party

import kotlinx.serialization.Serializable

@Serializable
data class RecruitmentList(
    val partyRecruitments: List<RecruitmentItem>,
    val total: Int,
)

@Serializable
data class RecruitmentItem(
    val id: Int,
    val recruitingCount: Int,
    val recruitedCount: Int,
    val content: String,
    val createdAt: String,
    val party: RecruitmentParty,
    val position: RecruitmentPosition,
)

@Serializable
data class RecruitmentParty(
    val id: Int,
    val title: String,
    val image: String?,
    val partyType: RecruitmentPartyType,
)

@Serializable
data class RecruitmentPartyType(
    val id: Int,
    val type: String,
)

@Serializable
data class RecruitmentPosition(
    val id: Int,
    val main: String,
    val sub: String,
)
