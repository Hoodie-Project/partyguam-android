package com.party.domain.model.party

import kotlinx.serialization.Serializable

@Serializable
data class PersonalRecruitmentListResponse(
    val parties: List<PersonalRecruitmentItemResponse>,
    val total: Int,
)

@Serializable
data class PersonalRecruitmentItemResponse(
    val partyRecruitmentId: Int,
    val main: String,
    val sub: String,
    val content: String,
    val recruitingCount: Int,
    val recruitedCount: Int,
    val applicationCount: Int,
    val createdAt: String,
    val party: PersonalRecruitmentPartyResponse,
    val position: PersonalRecruitmentPositionResponse,
)

@Serializable
data class PersonalRecruitmentPartyResponse(
    val title: String,
    val image: String,
)

@Serializable
data class PersonalRecruitmentPositionResponse(
    val id: Int,
    val main: String,
    val sub: String,
)