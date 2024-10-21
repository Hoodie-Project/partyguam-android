package com.party.domain.model.party

import kotlinx.serialization.Serializable

@Serializable
data class PersonalRecruitmentListResponse(
    val partyRecruitments: List<PersonalRecruitmentItemResponse>,
    val total: Int,
)

@Serializable
data class PersonalRecruitmentItemResponse(
    val id: Int,
    val partyId: Int,
    val positionId: Int,
    val recruitingCount: Int,
    val recruitedCount: Int,
    val content: String,
    val createdAt: String,
    val party: PersonalRecruitmentPartyResponse,
    val position: PersonalRecruitmentPositionResponse,
)

@Serializable
data class PersonalRecruitmentPartyResponse(
    val title: String,
    val image: String?,
)

@Serializable
data class PersonalRecruitmentPositionResponse(
    val id: Int,
    val main: String,
    val sub: String,
)