package com.party.domain.model.party

import kotlinx.serialization.Serializable

@Serializable
data class RecruitmentListResponse(
    val partyRecruitments: List<RecruitmentItemResponse>,
    val total: Int,
)

@Serializable
data class RecruitmentItemResponse(
    val id: Int,
    val partyId: Int,
    val positionId: Int,
    val recruitingCount: Int,
    val recruitedCount: Int,
    val content: String,
    val createdAt: String,
    val party: RecruitmentPartyResponse,
    val position: RecruitmentPositionResponse,
)

@Serializable
data class RecruitmentPartyResponse(
    val title: String,
    val image: String?,
    val partyType: RecruitmentPartyTypeResponse,
)

@Serializable
data class RecruitmentPartyTypeResponse(
    val id: Int,
    val type: String,
)

@Serializable
data class RecruitmentPositionResponse(
    val id: Int,
    val main: String,
    val sub: String,
)
